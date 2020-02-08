package ua.polina.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotUniqueFieldException extends RuntimeException{
    public NotUniqueFieldException(String message) {
        super(message);
    }
}
