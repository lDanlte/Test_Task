package com.dantonov.test_task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
public class ResponseMessageDto {

    
    
    @JsonProperty("msg")
    private String msg;

    
    
    public ResponseMessageDto() { }

    public ResponseMessageDto(String msg) { this.msg = msg; }

    
    
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    
}
