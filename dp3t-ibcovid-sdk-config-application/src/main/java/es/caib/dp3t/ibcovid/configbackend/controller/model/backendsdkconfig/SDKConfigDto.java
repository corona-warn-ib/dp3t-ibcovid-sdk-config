package es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SDKConfigDto implements Serializable {
    private static final long serialVersionUID = -568021786733598162L;

    Integer numberOfWindowsForExposure;
    @Deprecated
    Float eventThreshold;
    @Deprecated
    Float badAttenuationThreshold;
    Float contactAttenuationThreshold;

}
