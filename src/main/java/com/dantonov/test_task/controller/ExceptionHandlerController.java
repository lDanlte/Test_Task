package com.dantonov.test_task.controller;

import com.dantonov.test_task.dto.ResponseMessageDto;
import com.dantonov.test_task.exception.BadRequestException;
import com.dantonov.test_task.exception.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Перехват исключений с последующей записи информации о них в ответ
 * 
 * @author Antonov Denis (den007230@gmail.com)
 */

@ControllerAdvice
public class ExceptionHandlerController {

    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseMessageDto notFoundException(NotFoundException ex) {
        ResponseMessageDto message = new ResponseMessageDto(ex.getMessage());
        return message;
    }
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseMessageDto notFoundException(BadRequestException ex) {
        ResponseMessageDto message = new ResponseMessageDto(ex.getMessage());
        return message;
    }
    
}
