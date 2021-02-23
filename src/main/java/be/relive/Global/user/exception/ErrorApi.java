package be.relive.Global.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ErrorApi implements Serializable {

    private LocalDateTime timeStamp;

    private HttpStatus httpStatus;

    private String message;

    private String error;

    private String path;

    public ErrorApi() {
    }

    private ErrorApi(LocalDateTime timeStamp, HttpStatus httpStatus, String message, String error, String path) {
        this.timeStamp = timeStamp;
        this.httpStatus = httpStatus;
        this.message = message;
        this.error = error;
        this.path = path;
    }

    static ErrorApi errorApi(HttpStatus httpStatus, String message, WebRequest request){
        return new ErrorApi(LocalDateTime.now(), httpStatus, message, httpStatus.getReasonPhrase(),
                ((ServletWebRequest) request).getHttpMethod().name() + " " + request);
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
