package es.caib.dp3t.ibcovid.configbackend.data;

import es.caib.dp3t.ibcovid.configbackend.data.model.Config;
import es.caib.dp3t.ibcovid.configbackend.data.model.ConfigType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Long> {

    Config findByName(final ConfigType name);

}
