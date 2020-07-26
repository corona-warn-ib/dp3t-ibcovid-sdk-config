package es.caib.dp3t.ibcovid.configbackend.data.model.backendsdkconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class InfoBoxCollection implements Serializable {
    private static final long serialVersionUID = -1702699199991592760L;

    InfoBox deInfoBox;
    InfoBox frInfoBox;
    InfoBox itInfoBox;
    InfoBox enInfoBox;
    InfoBox ptInfoBox;
    InfoBox esInfoBox;
    InfoBox sqInfoBox;
    InfoBox bsInfoBox;
    InfoBox hrInfoBox;
    InfoBox srInfoBox;
    InfoBox rmInfoBox;

}
