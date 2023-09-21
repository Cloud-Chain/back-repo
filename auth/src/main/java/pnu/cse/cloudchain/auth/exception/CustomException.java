package pnu.cse.cloudchain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

//import static org.springframework.cloud.endpoint.event.RefreshEventListener.log;


@Slf4j
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    CustomExceptionStatus customExceptionStatus;
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;



    public CustomException(CustomExceptionStatus customExceptionStatus, String errorCode, String message) {
        this.customExceptionStatus = customExceptionStatus;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        log.error("errorcode  - traceid  - traceclass  -  tracemethod  - messagelog"); //  {}  {}  {}", customExceptionStatus, errorCode, message);
    }
}
