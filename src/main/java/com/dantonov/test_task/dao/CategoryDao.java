package com.dantonov.test_task.dao;

import com.dantonov.test_task.entity.Category;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
public interface CategoryDao {
    
    
    Category getById(Integer id);

    Iterable<Category> getAll();
    
}
