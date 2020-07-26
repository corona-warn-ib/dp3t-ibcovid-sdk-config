package es.caib.dp3t.ibcovid.configbackend.common.exception;

import lombok.Getter;

import java.text.MessageFormat;

@Getter
public class IBCovidBackendSDKConfigException extends RuntimeException {
    private static final long serialVersionUID = -6177757278500347535L;

    private final BackendSDKConfigErrorCodes error;

    public IBCovidBackendSDKConfigException(final BackendSDKConfigErrorCodes error, final Object... params) {
        super(MessageFormat.format(error.getMessage(), params));
        this.error = error;
    }

    public IBCovidBackendSDKConfigException(final Throwable cause, final BackendSDKConfigErrorCodes error, final Object... params) {
        super(MessageFormat.format(error.getMessage(), params), cause);
        this.error = error;
    }

}
