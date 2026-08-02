package co.in.techLakhan.Employee_Cache_demo.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private int errorCode;
    private String message;
    private HttpStatus status;

//    public ErrorResponse(int errorCode, String message, HttpStatus status) {
//        this.errorCode = errorCode;
//        this.message = message;
//        this.status = status;
//    }
}
