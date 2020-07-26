package es.caib.dp3t.ibcovid.configbackend.controller.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HeaderInjector implements HandlerInterceptor {
    private final Map<String, String> headers;

    public HeaderInjector(final Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public boolean preHandle(
            final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (headers != null) {
            for (final var header : headers.keySet()) {
                response.setHeader(header, headers.get(header));
            }
        }
        return true;
    }

}
