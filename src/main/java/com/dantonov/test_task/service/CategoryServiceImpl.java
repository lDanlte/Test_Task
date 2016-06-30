package com.dantonov.test_task.service;

import com.dantonov.test_task.dao.CategoryDao;
import com.dantonov.test_task.entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    
    
    @Autowired
    private CategoryDao categoryDao;

    
    
    @Override
    public Category getById(Integer id) {
        return categoryDao.getById(id);
    }

    @Override
    public Iterable<Category> getAll() {
        return categoryDao.getAll();
    }

}
