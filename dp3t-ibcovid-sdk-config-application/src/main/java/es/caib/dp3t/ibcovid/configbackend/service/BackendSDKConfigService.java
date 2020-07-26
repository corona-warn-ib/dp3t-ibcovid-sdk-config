package es.caib.dp3t.ibcovid.configbackend.service;

import es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig.BackendSDKConfigSrvDto;

public interface BackendSDKConfigService {

    BackendSDKConfigSrvDto getBackendSDKConfig();

    BackendSDKConfigSrvDto updateBackendSDKConfig(final BackendSDKConfigSrvDto srvRequest);

    BackendSDKConfigSrvDto getBackendSDKConfigForMobileApp(
            final String appversion, final String osversion, final String buildnr);

}
