package be.relive.Global.event.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static be.relive.Global.event.exception.ErrorApi.errorApi;


@ControllerAdvice
public class EventExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(EventExceptionHandler.class);

    @ExceptionHandler(EventNotFoundException.class)
    public final ResponseEntity handleInvalidQueryException(EventNotFoundException e, WebRequest request) {
        log.error("", e);
        ErrorApi error = errorApi(HttpStatus.BAD_REQUEST, e.getMessage(),request);
        return handleExceptionInternal(e, error,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }


}
