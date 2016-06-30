package com.dantonov.test_task.dao;

import com.dantonov.test_task.entity.Driver;

import java.util.List;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
public interface DriverDao {

    
    Driver getById(Integer id);
    
    Iterable<Driver> getAll();
    
    Iterable<Integer> getAllPolicy();
    
    List<Driver> getByFioAndPolicy(String fio, Integer policy);
    
    List<Driver> getByPolicy(Integer policy);
    
    Driver save(Driver driver);
    
}
