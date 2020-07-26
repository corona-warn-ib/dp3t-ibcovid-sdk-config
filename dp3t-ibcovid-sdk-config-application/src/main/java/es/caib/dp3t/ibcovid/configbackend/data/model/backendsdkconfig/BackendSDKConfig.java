package es.caib.dp3t.ibcovid.configbackend.data.model.backendsdkconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class BackendSDKConfig implements Serializable {
    private static final long serialVersionUID = 1010321030225054304L;

    boolean forceUpdate;
    boolean forceTraceShutdown;
    InfoBoxCollection infoBox;
    SDKConfig sdkConfig;
    GAENSDKConfig iosGaenSdkConfig;
    GAENSDKConfig androidGaenSdkConfig;

}
