package es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class BackendSDKConfigDto implements Serializable {
    private static final long serialVersionUID = 8438907744379181189L;

    @NotNull
    boolean forceUpdate;

    @NotNull
    boolean forceTraceShutdown;

    @Valid
    InfoBoxCollectionDto infoBox;

    @Valid
    @NotNull
    SDKConfigDto sdkConfig;

    @Valid
    @NotNull
    GAENSDKConfigDto iosGaenSdkConfig;

    @Valid
    @NotNull
    GAENSDKConfigDto androidGaenSdkConfig;

    @JsonGetter("iOSGaenSdkConfig")
    public GAENSDKConfigDto getIosGaenSdkConfig() {
        return iosGaenSdkConfig;
    }

    @JsonSetter("iOSGaenSdkConfig")
    public void setIosGaenSdkConfig(final GAENSDKConfigDto iosGaenSdkConfig) {
        this.iosGaenSdkConfig = iosGaenSdkConfig;
    }

}
