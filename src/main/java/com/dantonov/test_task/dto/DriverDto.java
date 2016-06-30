package com.dantonov.test_task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

import java.util.List;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDto {

    
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("fio")
    private String fio;
    
    @JsonProperty("birthday")
    private DateDto birthday;
    
    @JsonProperty("sex")
    private Byte sex;
    
    @JsonProperty("policy")
    private Integer policy;
    
    @JsonProperty("categories")
    private List<CategoryDto> categories;

    
    
    public DriverDto() { }
    
     public DriverDto(Integer id, String fio, LocalDate birthday) {
        this.id = id;
        this.fio = fio;
        this.birthday = new DateDto(birthday.getYear(), (byte) birthday.getMonthValue(), (byte) birthday.getDayOfMonth());
    }

    public DriverDto(Integer id, String fio, LocalDate birthday, Byte sex, Integer policy, List<CategoryDto> categories) {
        this.id = id;
        this.fio = fio;
        this.birthday = new DateDto(birthday.getYear(), (byte) birthday.getMonthValue(), (byte) birthday.getDayOfMonth());
        this.sex = sex;
        this.policy = policy;
        this.categories = categories;
    }

    
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFio() { return fio; }
    public void setFio(String fio) { this.fio = fio; }

    public DateDto getBirthday() { return birthday; }
    public void setBirthday(DateDto birthday) { this.birthday = birthday; }

    public Integer getPolicy() { return policy; }
    public void setPolicy(Integer policy) {  this.policy = policy; }

    public Byte getSex() { return sex; }
    public void setSex(Byte sex) { this.sex = sex; }

    public List<CategoryDto> getCategories() { return categories; }
    public void setCategories(List<CategoryDto> categories) { this.categories = categories; }
    
    
}
