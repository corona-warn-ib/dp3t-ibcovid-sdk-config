package es.caib.dp3t.ibcovid.configbackend.data.model.backendsdkconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GAENSDKConfig implements Serializable {
    private static final long serialVersionUID = -2408157370829403154L;

    Integer lowerThreshold;
    Integer higherThreshold;
    Double factorLow;
    Double factorHigh;
    Integer triggerThreshold;

}
