package es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InfoBoxCollectionSrvDto {
    InfoBoxSrvDto deInfoBox;
    InfoBoxSrvDto frInfoBox;
    InfoBoxSrvDto itInfoBox;
    InfoBoxSrvDto enInfoBox;
    InfoBoxSrvDto ptInfoBox;
    InfoBoxSrvDto esInfoBox;
    InfoBoxSrvDto sqInfoBox;
    InfoBoxSrvDto bsInfoBox;
    InfoBoxSrvDto hrInfoBox;
    InfoBoxSrvDto srInfoBox;
    InfoBoxSrvDto rmInfoBox;

}
