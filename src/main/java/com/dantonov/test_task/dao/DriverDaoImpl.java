package com.dantonov.test_task.dao;

import com.dantonov.test_task.annotation.Transaction;
import com.dantonov.test_task.entity.Category;
import com.dantonov.test_task.entity.Driver;

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
public class DriverDaoImpl implements DriverDao {
    
    
    @Resource
    @Qualifier("driverStorage")
    private Map<Integer, Driver> driverStorage;
    
    @Resource
    @Qualifier("policyStorage")
    private Map<Integer, List<Driver>> policyStorage;
    
    @Resource
    @Qualifier("categoryStorage")
    private Map<Integer, Category> categoryStorage;
    

    
    @Override
    @Transaction(resource = "driver")
    public Driver getById(Integer id) {
        return driverStorage.get(id).clone();
    }

    @Override
    @Transaction(resource = "driver")
    public Iterable<Driver> getAll() {
        return cloneAll(driverStorage.values());
    }
    
    @Override
    @Transaction(resource = "policy")
    public Iterable<Integer> getAllPolicy() {
        return policyStorage.keySet();
    }
    
    
    @Override
    @Transaction(resource = "driver")
    public List<Driver> getByFioAndPolicy(String fio, Integer policy) {
        
        List<Driver> result = new ArrayList<>();
        String[] strs = fio.split(" ");
        
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].toLowerCase();
        }
        
        for (Driver driver : policyStorage.get(policy)) {
            
            String currentFio = driver.getFio().toLowerCase();
            boolean isLike = true;
            
            for (int i = 0; i < strs.length && isLike; i++) {
                isLike = isLike && currentFio.contains(strs[i]);
            }
            
            if (isLike) {
                result.add(driver);
            }
        }
        List<Driver> cloned = cloneAll(result);
        return (cloned.isEmpty()) ? null : cloned;
    }

    
    @Override
    @Transaction(resource = "driver")
    public List<Driver> getByPolicy(Integer policy) {
        return cloneAll(policyStorage.get(policy));
    }

    
    @Override
    @Transaction(resource = "driver", readOnly = false)
    public Driver save(Driver driver) {
        
        if (!driverStorage.containsKey(driver.getId())) {
            return null;
        }
        
        Driver oldDriver = driverStorage.get(driver.getId());
        
        oldDriver.setBirthday(driver.getBirthday());
        oldDriver.setFio(driver.getFio());
        oldDriver.setSex(driver.getSex());
        
        List<Category> categories = new ArrayList<>(driver.getCategories().size());
        
        for (Category c : driver.getCategories()) {
            categories.add(categoryStorage.get(c.getId()));
        }
        
        oldDriver.setCategories(categories);
        
        if (!oldDriver.getPolicy().equals(driver.getPolicy())) {
            
            if (policyStorage.containsKey(oldDriver.getPolicy())) {
                policyStorage.get(oldDriver.getPolicy()).remove(oldDriver);
            }
            
            oldDriver.setPolicy(driver.getPolicy());
            
            if (driver.getPolicy() != null) {
                
                if (policyStorage.containsKey(driver.getPolicy())) {
                    policyStorage.get(driver.getPolicy()).add(oldDriver);
                } else {
                    List<Driver> drivers = new ArrayList<>();
                    drivers.add(oldDriver);
                    policyStorage.put(driver.getPolicy(), drivers);
                }
            }
        }
        
        return oldDriver.clone();
    }
    
    
    
    
    private List<Driver> cloneAll(Iterable<Driver> drivers) {
        
        List<Driver> result = new ArrayList<>();
        
        for (Driver d : drivers) {
            result.add(d.clone());
        }
        
        return result;
    }
    

}
