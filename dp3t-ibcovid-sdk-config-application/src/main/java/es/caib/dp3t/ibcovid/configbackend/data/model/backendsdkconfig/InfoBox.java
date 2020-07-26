package es.caib.dp3t.ibcovid.configbackend.data.model.backendsdkconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class InfoBox implements Serializable {
    private static final long serialVersionUID = -1942776047059136515L;

    String title;
    String msg;
    String url;
    String urlTitle;

}
