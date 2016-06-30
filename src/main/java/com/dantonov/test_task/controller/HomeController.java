package com.dantonov.test_task.controller;

import com.dantonov.test_task.service.CategoryService;
import com.dantonov.test_task.service.DriverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Контроллер корневого пути
 * @author Antonov Denis (den007230@gmail.com)
 */

@Controller
public class HomeController {
    
    
    
    @Autowired
    private DriverService driverService;
    
    @Autowired
    private CategoryService categoryService;
    
    
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/",
                    method = RequestMethod.GET,
                    produces = MediaType.TEXT_HTML_VALUE)
    public String driverEditor(Model model) {
        
        model.addAttribute("policyIds", driverService.getAllPolicy());
        model.addAttribute("categories", categoryService.getAll());
        
        return "driverEditor";
    }

}
