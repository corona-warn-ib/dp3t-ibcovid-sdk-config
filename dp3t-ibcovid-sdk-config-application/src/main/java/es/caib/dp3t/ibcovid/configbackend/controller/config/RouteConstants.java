package es.caib.dp3t.ibcovid.configbackend.controller.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class RouteConstants {
    public static final String BASE_PATH = "/v1";
    public static final String ADMIN_PATH = BASE_PATH + "/admin";

    public static final String ACTUATOR_BASE_PATH = "/actuator";
    public static final String H2_CONSOLE_PATH = "/h2-console";

    public static final String SDK_CONFIG_BASE_PATH = BASE_PATH;
    public static final String SDK_CONFIG_ADMIN_BASE_PATH = ADMIN_PATH + "/config";

}
