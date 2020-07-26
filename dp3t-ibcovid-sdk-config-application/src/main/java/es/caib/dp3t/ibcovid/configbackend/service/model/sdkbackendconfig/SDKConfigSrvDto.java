package es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SDKConfigSrvDto {
    Integer numberOfWindowsForExposure;
    @Deprecated
    Float eventThreshold;
    @Deprecated
    Float badAttenuationThreshold;
    Float contactAttenuationThreshold;

}
