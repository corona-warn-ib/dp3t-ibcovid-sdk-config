package es.caib.dp3t.ibcovid.configbackend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.caib.dp3t.ibcovid.configbackend.data.model.Config;
import es.caib.dp3t.ibcovid.configbackend.data.model.ConfigType;
import es.caib.dp3t.ibcovid.configbackend.data.model.backendsdkconfig.BackendSDKConfig;
import es.caib.dp3t.ibcovid.configbackend.service.mapper.BackendSdkConfigSrvDtoMapper;
import lombok.extern.log4j.Log4j2;
import es.caib.dp3t.ibcovid.configbackend.common.exception.IBCovidBackendSDKConfigException;
import es.caib.dp3t.ibcovid.configbackend.common.exception.BackendSDKConfigErrorCodes;
import es.caib.dp3t.ibcovid.configbackend.common.utils.DateUtils;
import es.caib.dp3t.ibcovid.configbackend.data.ConfigRepository;
import es.caib.dp3t.ibcovid.configbackend.service.BackendSDKConfigService;
import es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig.BackendSDKConfigSrvDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
class BackendSDKConfigImpl implements BackendSDKConfigService {

    private final ConfigRepository configRepository;
    private final ObjectMapper objectMapper;

    public BackendSDKConfigImpl(final ConfigRepository configRepository,
                                final ObjectMapper objectMapper) {
        this.configRepository = configRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public BackendSDKConfigSrvDto getBackendSDKConfig() {
        log.debug("BEGIN - getBackendSDKConfig");

        final BackendSDKConfig config = getConfig(ConfigType.BACKEND_SDK_CONFIG, BackendSDKConfig.class);
        final BackendSDKConfigSrvDto response = BackendSdkConfigSrvDtoMapper.INSTANCE.entityToSrvDto(config);

        log.debug("END - getBackendSDKConfig: response={}", response);
        return response;
    }

    @Override
    @Transactional
    public BackendSDKConfigSrvDto updateBackendSDKConfig(final BackendSDKConfigSrvDto srvRequest) {
        log.debug("BEGIN - updateBackendSDKConfig");

        final BackendSDKConfig config = BackendSdkConfigSrvDtoMapper.INSTANCE.srvDtoToEntity(srvRequest);
        setConfig(ConfigType.BACKEND_SDK_CONFIG, config);
        final BackendSDKConfigSrvDto response = BackendSdkConfigSrvDtoMapper.INSTANCE.entityToSrvDto(config);

        log.debug("END - updateBackendSDKConfig: response={}", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public BackendSDKConfigSrvDto getBackendSDKConfigForMobileApp(
            final String appversion, final String osversion, final String buildnr) {
        return getBackendSDKConfig();
    }

    private <T> T getConfig(final ConfigType configType, final Class<T> classToConvertTo) {
        final Config config = configRepository.findByName(configType);
        if (config == null) {
            return null;
        }

        try {
            return jsonToObject(config.getValue(), classToConvertTo);
        } catch (final JsonProcessingException e) {
            throw new IBCovidBackendSDKConfigException(BackendSDKConfigErrorCodes.GENERAL_ERROR, configType, classToConvertTo);
        }
    }

    private <T> void setConfig(final ConfigType configType, final T objectToBeSet) {
        try {
            final String json = objectToJson(objectToBeSet);

            Config config = configRepository.findByName(configType);

            if (config != null) {
                config.setValue(json);
                config.setModifiedAt(DateUtils.currentUTCLocalDateTime());
            } else {
                config = Config.builder()
                        .name(configType)
                        .value(json)
                        .createdAt(DateUtils.currentUTCLocalDateTime())
                        .build();
            }

            configRepository.save(config);
        } catch (final JsonProcessingException e) {
            throw new IBCovidBackendSDKConfigException(BackendSDKConfigErrorCodes.GENERAL_ERROR, configType, objectToBeSet.getClass());
        }
    }

    private <T> String objectToJson(final T objectToBeSet) throws JsonProcessingException {
        return objectMapper.writeValueAsString(objectToBeSet);
    }

    private <T> T jsonToObject(final String json, final Class<T> classToConvertTo) throws JsonProcessingException {
        return objectMapper.readValue(json, classToConvertTo);
    }

}
