package com.dantonov.test_task.controller;


import com.dantonov.test_task.dto.CategoryDto;
import com.dantonov.test_task.dto.DriverDto;
import com.dantonov.test_task.entity.Category;
import com.dantonov.test_task.entity.Driver;
import com.dantonov.test_task.exception.BadRequestException;
import com.dantonov.test_task.exception.NotFoundException;
import com.dantonov.test_task.service.CategoryService;
import com.dantonov.test_task.service.DriverService;

import java.time.DateTimeException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Основные операции с данными водителей
 * 
 * @author Antonov Denis (den007230@gmail.com)
 */

@Controller
public class DriverController {
    
    
    
    @Autowired
    private DriverService driverService;
    
    @Autowired
    private CategoryService categoryService;
    
    
    
    //Поиск водителей по полису и имени
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/policy/{id}/drivers",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DriverDto> searchByFio(@PathVariable("id") Integer policyId, @RequestParam("q") String query) {
        
       List<Driver> drivers = driverService.getByFioAndPolicy(query, policyId);
       
       if (drivers == null) {
           throw new NotFoundException("Водитель не найден.");
       }
       
       List<DriverDto> result = new ArrayList<>(drivers.size());
       
       for (Driver d : drivers) {
           result.add(toShortDriverDto(d));
       }
       
       return result;
    }
    
    
    //Обновление информации о водителе
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/drivers/{id}",
                    method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DriverDto update(@PathVariable("id") Integer id, 
                            @RequestBody DriverDto driverDto) {
        
        if (driverService.getById(id) == null) {
            throw new NotFoundException("Водителя с данным id нет.");
        }
        
        Driver newDriver = driverService.save(toDriver(driverDto));
        
        return toDriverDto(newDriver);
    }
    
    
    //Получение водителя по его id
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/drivers/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DriverDto getDriver(@PathVariable("id") Integer id) {
        
        Driver driver = driverService.getById(id);
        
        if (driver == null) {
            throw new NotFoundException("Водителя с данным id нет.");
        }
        
        return toDriverDto(driver);
    }
    
    
    //Удаление водителя из данного полиса
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/policy/{id}/drivers/{driverId}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void delete(@PathVariable("id") Integer policyId,
                       @PathVariable("driverId") Integer driverId) {
        
        Driver dr = driverService.getById(driverId);
        
        if (dr == null) {
            throw new NotFoundException("Водителя с данным id нет.");
        }
        
        if (!dr.getPolicy().equals(policyId)) {
            throw new BadRequestException("На водителя не зарегистрирован данный полис");
        }
        
        dr.setPolicy(null);
        driverService.save(dr);
        
    }

    
    
    private DriverDto toDriverDto(Driver d) {
        
        List<CategoryDto> categories = new ArrayList<>(d.getCategories().size());
        
        for (Category category : d.getCategories()) {
            categories.add(new CategoryDto(category.getId(), category.getName()));
        }
        
        DriverDto result = new DriverDto(d.getId(), d.getFio(), d.getBirthday(), d.getSex(), d.getPolicy(), categories);
        
        return result;
        
    }
    
    
    private DriverDto toShortDriverDto(Driver d) {
        return new DriverDto(d.getId(), d.getFio(), d.getBirthday());
    }
    
    
    private Driver toDriver(DriverDto dto) {
        
        if (dto.getCategories().isEmpty()) {
            throw new BadRequestException("У водителя не может не быть категорий.");
        }
        
        List<Category> categories = new ArrayList<>(dto.getCategories().size());
        
        for (CategoryDto categoryDto : dto.getCategories()) {
            Category c = categoryService.getById(categoryDto.getId());
            if (c == null) {
                throw new BadRequestException("Запрос содержит неизвестную категорию.");
            }
            categories.add(categoryService.getById(categoryDto.getId()));
        }
        
        LocalDate birthDate;
        
        try {
            birthDate = LocalDate.of(dto.getBirthday().getYear(),
                                     dto.getBirthday().getMonth(),
                                     dto.getBirthday().getDay());
        } catch (DateTimeException ex) {
            throw new BadRequestException("Неверно задана дата.");
        }
        
        return new Driver(dto.getId(), dto.getFio(), 
                birthDate,
                dto.getSex(), dto.getPolicy(), categories);
    }
}
