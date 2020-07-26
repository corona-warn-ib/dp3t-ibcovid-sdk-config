package es.caib.dp3t.ibcovid.configbackend.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("dp3t.ibcovid.codegen")
public class AppProperties {
    private String publicKey;
    private Long expirationDuration;

}
