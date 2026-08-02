package co.in.techLakhan.Employee_Cache_demo.exception;

import org.springframework.http.HttpStatus;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

public class EmployeeNotFoundException extends Exception {

    private final int errorCode;
    private final HttpStatus status;

    public EmployeeNotFoundException(int errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
