package com.dantonov.test_task.service;

import com.dantonov.test_task.dao.DriverDao;
import com.dantonov.test_task.entity.Driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */

@Service
public class DriverServiceImpl implements DriverService {

    
    
    @Autowired
    private DriverDao driverDao;

    
    
    @Override
    public Driver getById(Integer id) {
        return driverDao.getById(id);
    }

    @Override
    public Iterable<Driver> getAll() {
        return driverDao.getAll();
    }
    
    @Override
    public Iterable<Integer> getAllPolicy() {
        return driverDao.getAllPolicy();
    }

    @Override
    public List<Driver> getByFioAndPolicy(String fio, Integer policy) {
        return driverDao.getByFioAndPolicy(fio, policy);
    }

    @Override
    public List<Driver> getByPolicy(Integer policy) {
        return driverDao.getByPolicy(policy);
    }

    @Override
    public Driver save(Driver driver) {
        return driverDao.save(driver);
    }
    
    
    
}
