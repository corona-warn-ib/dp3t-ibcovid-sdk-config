package es.caib.dp3t.ibcovid.configbackend.controller.model.backendsdkconfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class InfoBoxDto implements Serializable {
    private static final long serialVersionUID = 744401320506487987L;
    String title;
    String msg;
    String url;
    String urlTitle;

}
