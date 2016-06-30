package com.dantonov.test_task.service;

import com.dantonov.test_task.entity.Category;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
public interface CategoryService {
    
    
    Category getById(Integer id);

    Iterable<Category> getAll();

}
