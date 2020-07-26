package es.caib.dp3t.ibcovid.configbackend.controller.errorhandler;

import es.caib.dp3t.ibcovid.configbackend.common.exception.BackendSDKConfigErrorCodes;
import es.caib.dp3t.ibcovid.configbackend.common.exception.IBCovidBackendSDKConfigException;
import lombok.extern.log4j.Log4j2;
import es.caib.dp3t.ibcovid.configbackend.controller.model.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class BackendSDKConfigExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorMessageDto>> handleException(final Exception ex) {
        final IBCovidBackendSDKConfigException exception = new IBCovidBackendSDKConfigException(
                ex, BackendSDKConfigErrorCodes.GENERAL_ERROR, "If it persists, please contact the administrator");
        return processExceptionAndLogError(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage());
        final List<ErrorMessageDto> errorMessage = getErrorMessages(ex.getBindingResult());
        return buildResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IBCovidBackendSDKConfigException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleIBCovidCodeGenException(final IBCovidBackendSDKConfigException ex) {
        return processExceptionAndLogError(ex);
    }

    private ResponseEntity<List<ErrorMessageDto>> processExceptionAndLogError(final IBCovidBackendSDKConfigException ex) {
        log.error(ex.getMessage(), ex);
        final ErrorMessageDto errorMessage = getErrorMessage(ex);
        return buildResponse(errorMessage, ex.getError().getHttpStatus());
    }

    private ErrorMessageDto getErrorMessage(final IBCovidBackendSDKConfigException ex) {
        return ErrorMessageDto.builder()
                .code(ex.getError().getCode())
                .message(ex.getMessage())
                .build();
    }

    private List<ErrorMessageDto> getErrorMessages(final BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(this::getErrorMessage)
                .collect(Collectors.toList());
    }

    private ErrorMessageDto getErrorMessage(final ObjectError error) {
        final String errorMessage;
        if (error instanceof FieldError) {
            final FieldError fieldError = (FieldError) error;
            errorMessage = String.format("field=[%s], message=[%s]", fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            errorMessage = error.getDefaultMessage();
        }

        return ErrorMessageDto.builder()
                .code(BackendSDKConfigErrorCodes.VALIDATION_ERROR.getCode())
                .message(errorMessage)
                .build();
    }

    private ResponseEntity<List<ErrorMessageDto>> buildResponse(
            final ErrorMessageDto errorMessage, final HttpStatus httpStatus) {
        return buildResponse(Collections.singletonList(errorMessage), httpStatus);
    }

    private ResponseEntity<List<ErrorMessageDto>> buildResponse(
            final List<ErrorMessageDto> errorMessages, final HttpStatus httpStatus) {
        return new ResponseEntity<>(errorMessages, httpStatus);
    }

}
