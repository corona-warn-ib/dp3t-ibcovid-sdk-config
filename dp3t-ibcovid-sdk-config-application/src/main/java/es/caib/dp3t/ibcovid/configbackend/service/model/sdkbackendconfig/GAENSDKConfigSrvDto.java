package es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GAENSDKConfigSrvDto {
    Integer lowerThreshold;
    Integer higherThreshold;
    Double factorLow;
    Double factorHigh;
    Integer triggerThreshold;

}
