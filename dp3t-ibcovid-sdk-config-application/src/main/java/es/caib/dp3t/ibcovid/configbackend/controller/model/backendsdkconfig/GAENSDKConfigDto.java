package es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GAENSDKConfigDto implements Serializable {
    private static final long serialVersionUID = -5152487157482517621L;

    Integer lowerThreshold;
    Integer higherThreshold;
    Double factorLow;
    Double factorHigh;
    Integer triggerThreshold;

}
