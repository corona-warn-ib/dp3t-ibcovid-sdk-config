package es.caib.dp3t.ibcovid.configbackend.controller.filter;

import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import es.caib.dp3t.ibcovid.configbackend.controller.client.auth.IBCovidAuthClient;
import es.caib.dp3t.ibcovid.configbackend.controller.client.auth.model.UserBasicDetailsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    final IBCovidAuthClient ibcovidWarnAuthClient;

    public JWTAuthorizationFilter(final AuthenticationManager authenticationManager,
                                  final IBCovidAuthClient ibcovidWarnAuthClient) {
        super(authenticationManager);
        this.ibcovidWarnAuthClient = ibcovidWarnAuthClient;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain)
            throws IOException, ServletException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!requiresAuthentication(header)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            final Map<String, Object> headers = Map.of(HttpHeaders.AUTHORIZATION, header);
            final UserBasicDetailsDto userBasicDetails = ibcovidWarnAuthClient.authorize(headers);
            if (userBasicDetails != null) {
                final String username = userBasicDetails.getUsername();
                final List<GrantedAuthority> authorities = userBasicDetails.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                final UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                log.warn("Request not authenticated");
                SecurityContextHolder.clearContext();
            }
        } catch (final FeignException ex) {
            log.warn("Request not authenticated", ex);
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    private boolean requiresAuthentication(final String header) {
        return StringUtils.startsWith(header, "Bearer ");
    }

}
