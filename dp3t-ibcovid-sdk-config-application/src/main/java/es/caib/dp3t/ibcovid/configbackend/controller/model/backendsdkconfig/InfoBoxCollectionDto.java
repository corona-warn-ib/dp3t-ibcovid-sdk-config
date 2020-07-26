package es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class InfoBoxCollectionDto implements Serializable {
    private static final long serialVersionUID = -7902290714805766886L;

    InfoBoxDto deInfoBox;
    InfoBoxDto frInfoBox;
    InfoBoxDto itInfoBox;
    InfoBoxDto enInfoBox;
    InfoBoxDto ptInfoBox;
    InfoBoxDto esInfoBox;
    InfoBoxDto sqInfoBox;
    InfoBoxDto bsInfoBox;
    InfoBoxDto hrInfoBox;
    InfoBoxDto srInfoBox;
    InfoBoxDto rmInfoBox;

}
