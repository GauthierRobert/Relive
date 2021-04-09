package be.relive.Global.user.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static be.relive.Global.user.exception.ErrorApi.errorApi;


@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleDefaultException(Exception e, WebRequest request) {
        log.error("", e);
        ErrorApi error = errorApi(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),request);
        return handleExceptionInternal(e, error,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

    }


}
