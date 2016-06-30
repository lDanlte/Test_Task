package com.dantonov.test_task.entity;

import java.time.LocalDate;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
public class Driver implements Cloneable {

    
    private Integer id;
    
    private String fio;
    
    private LocalDate birthday;
    
    private Byte sex;
    
    private Integer policy;
    
    private List<Category> categories;

    
    
    public Driver() { }

    public Driver(Integer id, String fio, LocalDate birthday, Byte sex,
                  Integer policy, List<Category> categories) {
        this.id = id;
        this.fio = fio;
        this.birthday = birthday;
        this.sex = sex;
        this.policy = policy;
        this.categories = categories;
    }

    
    
    public Integer getId() { return id; }
    public void setId(Integer id) {  this.id = id; }

    public String getFio() { return fio; }
    public void setFio(String fio) { this.fio = fio; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public Integer getPolicy() { return policy; }
    public void setPolicy(Integer policy) { this.policy = policy; }

    public Byte getSex() { return sex; }
    public void setSex(Byte sex) {  this.sex = sex; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }

    
    @Override
    public int hashCode() {
        
        HashCodeBuilder hcb = new HashCodeBuilder();
        
        hcb.append(id);
        hcb.append(fio);
        hcb.append(birthday);
        hcb.append(policy);
        hcb.append(sex);
        hcb.append(categories);
        
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Driver)) return false;
        
        Driver other = (Driver) obj;
        
        EqualsBuilder eb = new EqualsBuilder();
        
        eb.append(this.id, other.id);
        eb.append(this.fio, other.fio);
        eb.append(this.birthday, other.birthday);
        eb.append(this.policy, other.policy);
        eb.append(this.sex, other.sex);
        eb.append(this.categories, other.categories);
        
        return eb.isEquals();
    }

    @Override
    public Driver clone(){
        
        try {
            return (Driver) super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println("Clone unsupported");
        }
        
        return null;
    }
    
    
    
}
