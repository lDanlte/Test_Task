package com.dantonov.test_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Бросается при некорректном запросе
 * 
 * @author Antonov Denis (den007230@gmail.com)
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super();
    }
    
    

    public BadRequestException(String message) {
        super(message, null, false, false);
    }

     
    
}
