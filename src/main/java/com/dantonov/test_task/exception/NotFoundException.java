package com.dantonov.test_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Бросается при отсутствии ресурса для данного запроса
 * 
 * @author Antonov Denis (den007230@gmail.com)
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    
    public NotFoundException() {
        super();
    }


    public NotFoundException(String message) {
        super(message, null, false, false);
    }
    
}
