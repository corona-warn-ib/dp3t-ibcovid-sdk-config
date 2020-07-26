package es.caib.dp3t.ibcovid.configbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConfigBackendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ConfigBackendApplication.class);
    }

}
