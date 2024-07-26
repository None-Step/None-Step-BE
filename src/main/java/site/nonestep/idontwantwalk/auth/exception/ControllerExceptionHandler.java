package site.nonestep.idontwantwalk.auth.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ErrorResponse.class})
    protected ResponseEntity handleCustomException(ErrorResponse e) {

        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("statusCode", e.getStatusCode());
        map.put("message", e.getMessage());

        return new ResponseEntity(map, resHeaders, e.getStatusCode());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleServerException(Exception e) {

        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        return new ResponseEntity(e.getMessage(), resHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
