package es.caib.dp3t.ibcovid.configbackend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import es.caib.dp3t.ibcovid.configbackend.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.configbackend.controller.mapper.BackendSDKConfigDtoMapper;
import es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig.BackendSDKConfigDto;
import es.caib.dp3t.ibcovid.configbackend.service.BackendSDKConfigService;
import es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig.BackendSDKConfigSrvDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = RouteConstants.SDK_CONFIG_ADMIN_BASE_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
@Log4j2
@Api(tags = "Administration of the backend SDK configuration operations")
public class AdminBackendSDKConfigController {

    private final BackendSDKConfigService backendSDKConfigService;

    public AdminBackendSDKConfigController(final BackendSDKConfigService backendSDKConfigService) {
        this.backendSDKConfigService = backendSDKConfigService;
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Gets the backend SDK configuration")
    public ResponseEntity<BackendSDKConfigDto> getBackendSDKConfig() {
        log.debug("BEGIN - getBackendSDKConfig");

        final BackendSDKConfigSrvDto srvResponse = backendSDKConfigService.getBackendSDKConfig();
        final BackendSDKConfigDto response = BackendSDKConfigDtoMapper.INSTANCE.srvDtoToDto(srvResponse);

        log.debug("END - getBackendSDKConfig: response={}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Updates the backend SDK configuration")
    public ResponseEntity<BackendSDKConfigDto> updateBackendSDKConfig(
            @Valid @RequestBody final BackendSDKConfigDto request) {
        log.debug("BEGIN - updateBackendSDKConfig: params={}", request);

        final BackendSDKConfigSrvDto srvRequest = BackendSDKConfigDtoMapper.INSTANCE.dtoToSrvDto(request);
        final BackendSDKConfigSrvDto srvResponse = backendSDKConfigService.updateBackendSDKConfig(srvRequest);
        final BackendSDKConfigDto response = BackendSDKConfigDtoMapper.INSTANCE.srvDtoToDto(srvResponse);

        log.debug("END - updateBackendSDKConfig: response={}", response);
        return ResponseEntity.ok(response);
    }

}
