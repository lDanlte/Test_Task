package com.dantonov.test_task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {

    
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("name")
    private String name;

    @JsonProperty("desc")
    private String desc;

    
    
    public CategoryDto() { }
    
    public CategoryDto(Integer id) {
        this.id = id;
    }

    public CategoryDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public CategoryDto(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    
    
    
}
