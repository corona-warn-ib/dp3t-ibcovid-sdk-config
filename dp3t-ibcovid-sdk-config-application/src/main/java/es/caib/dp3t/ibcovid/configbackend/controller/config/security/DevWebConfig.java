package es.caib.dp3t.ibcovid.configbackend.controller.config.security;

import es.caib.dp3t.ibcovid.configbackend.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.configbackend.controller.filter.JWTAuthorizationFilter;
import es.caib.dp3t.ibcovid.configbackend.controller.client.auth.IBCovidAuthClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@Profile("dev")
public class DevWebConfig extends BaseWebConfig {

    // TODO For now the client code is included in every repo.
    // Needs to be deleted and add the maven dependency for the client project, when an artifactory is available
    private final IBCovidAuthClient ibcovidWarnAuthClient;

    public DevWebConfig(final IBCovidAuthClient ibcovidWarnAuthClient) {
        this.ibcovidWarnAuthClient = ibcovidWarnAuthClient;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(antPatternUrlStartsWith(RouteConstants.ADMIN_PATH)).hasAnyRole("SDK_ADMIN")
                .antMatchers(antPatternUrlStartsWith(RouteConstants.SDK_CONFIG_BASE_PATH),
                        antPatternUrlStartsWith(RouteConstants.ACTUATOR_BASE_PATH),
                        antPatternUrlStartsWith(RouteConstants.H2_CONSOLE_PATH))
                .permitAll()
//                .anyRequest().denyAll()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), ibcovidWarnAuthClient))
                .cors().and()
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
