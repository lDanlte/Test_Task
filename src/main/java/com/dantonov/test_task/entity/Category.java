package com.dantonov.test_task.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
public class Category implements Cloneable {
    

    
    private Integer id;
    
    private String name;

    private String desc;
    
    
    
    public Category() { }

    public Category(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name;  }
    public void setName(String name) { this.name = name; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    
    @Override
    public int hashCode() {
        
        HashCodeBuilder hcb = new HashCodeBuilder();
        
        hcb.append(id);
        hcb.append(name);
        hcb.append(desc);
        
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Category)) return false;
        
        Category other = (Category) obj;
        
        EqualsBuilder eb = new EqualsBuilder();
        
        eb.append(this.id, other.id);
        eb.append(this.name, other.name);
        eb.append(this.desc, other.desc);
        
        return eb.isEquals();
    }

    @Override
    public Category clone() {
        
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println("Clone unsupported");
        }
        return null;
    }
    
     
    
}
