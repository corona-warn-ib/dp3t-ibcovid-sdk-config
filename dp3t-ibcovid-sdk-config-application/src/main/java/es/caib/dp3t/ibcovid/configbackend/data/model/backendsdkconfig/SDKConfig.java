package es.caib.dp3t.ibcovid.configbackend.data.model.backendsdkconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SDKConfig implements Serializable {
    private static final long serialVersionUID = -3072395488943280281L;

    Integer numberOfWindowsForExposure;
    @Deprecated
    Float eventThreshold;
    @Deprecated
    Float badAttenuationThreshold;
    Float contactAttenuationThreshold;

}
