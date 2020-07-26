package es.caib.dp3t.ibcovid.configbackend.service.model.sdkbackendconfig;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InfoBoxSrvDto {
    String title;
    String msg;
    String url;
    String urlTitle;

}
