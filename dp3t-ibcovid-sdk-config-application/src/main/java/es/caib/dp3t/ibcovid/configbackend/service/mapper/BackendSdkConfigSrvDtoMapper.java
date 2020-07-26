package es.caib.dp3t.ibcovid.configbackend.service.mapper;

import es.caib.dp3t.ibcovid.configbackend.data.model.backendsdkconfig.BackendSDKConfig;
import es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig.BackendSDKConfigSrvDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BackendSdkConfigSrvDtoMapper {

    BackendSdkConfigSrvDtoMapper INSTANCE = Mappers.getMapper(BackendSdkConfigSrvDtoMapper.class);

    BackendSDKConfig srvDtoToEntity(final BackendSDKConfigSrvDto srvRequest);

    BackendSDKConfigSrvDto entityToSrvDto(final BackendSDKConfig entity);

}
