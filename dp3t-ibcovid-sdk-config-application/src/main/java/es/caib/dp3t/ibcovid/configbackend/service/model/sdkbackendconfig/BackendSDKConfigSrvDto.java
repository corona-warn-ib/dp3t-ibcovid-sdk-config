package es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BackendSDKConfigSrvDto {
    boolean forceUpdate;
    boolean forceTraceShutdown;
    InfoBoxCollectionSrvDto infoBox;
    SDKConfigSrvDto sdkConfig;
    GAENSDKConfigSrvDto iosGaenSdkConfig;
    GAENSDKConfigSrvDto androidGaenSdkConfig;

}
