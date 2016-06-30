package com.dantonov.test_task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateDto {
    
    
    
    @JsonProperty("year")
    private Integer year;
    
    @JsonProperty("month")
    private Byte month;
    
    @JsonProperty("day")
    private Byte day;

    
    
    public DateDto() {
    }

    public DateDto(Integer year, Byte month, Byte day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    
    
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Byte getMonth() { return month; }
    public void setMonth(Byte month) { this.month = month; }

    public Byte getDay() { return day; }
    public void setDay(Byte day) { this.day = day; }
    
    
}
