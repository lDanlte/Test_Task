package com.dantonov.test_task.dao;

import com.dantonov.test_task.annotation.Transaction;
import com.dantonov.test_task.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */

@Component
public class CategoryDaoImpl implements CategoryDao {
    
    
    
    @Resource
    @Qualifier("categoryStorage")
    private Map<Integer, Category> categoryStorage;
    
    
    
    @Override
    @Transaction(resource = "category")
    public Category getById(Integer id) {
        return categoryStorage.get(id).clone();
    }
    
    
    @Override
    @Transaction(resource = "category")
    public Iterable<Category> getAll() {
        return cloneAll(categoryStorage.values());
    }

    
    
    private List<Category> cloneAll(Iterable<Category> categories) {
        
        List<Category> result = new ArrayList<>();
        
        for (Category d : categories) {
            result.add(d.clone());
        }
        
        return result;
    }
    
}
