package com.dantonov.test_task.content;

import com.dantonov.test_task.entity.Category;
import com.dantonov.test_task.entity.Driver;

import java.time.LocalDate;
import java.time.Month;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Заполнение данными хранилища
 * 
 * @author Antonov Denis (den007230@gmail.com)
 */
public class DataContent {
    
    
    public static void fillCategories(Map<Integer, Category> storage) {
        
        if (!storage.isEmpty()) return;
        
        storage.put(1,  new Category(1,  "A",   "Мотоциклы"));
        storage.put(2,  new Category(2,  "A1",  "Легкие мотоциклы"));
        storage.put(3,  new Category(3,  "B",   "Легковые автомобили, небольшие грузовики (до 3,5 тонн)"));
        storage.put(4,  new Category(4,  "BE",  "Легковые автомобили с прицепом"));
        storage.put(5,  new Category(5,  "B1",  "Трициклы"));
        storage.put(6,  new Category(6,  "C",   "Грузовые автомобили (от 3,5 тонн)"));
        storage.put(7,  new Category(7,  "CE",  "Грузовые автомобили с прицепом"));
        storage.put(8,  new Category(8,  "C1",  "Средние грузовики (от 3,5 до 7,5 тонн)"));
        storage.put(9,  new Category(9,  "C1E", "Средние грузовики с прицепом"));
        storage.put(10, new Category(10, "D",   "Автобусы"));
        storage.put(11, new Category(11, "DE",  "Автобусы с прицепом"));
        storage.put(12, new Category(12, "D1",  "Небольшие автобусы"));
        storage.put(13, new Category(13, "D1E", "Небольшие автобусы с прицепом"));
        storage.put(14, new Category(14, "M",   "Мопеды"));
        storage.put(15, new Category(15, "Tm",  "Трамваи"));
        storage.put(16, new Category(16, "Tb",  "Троллейбусы"));
        
    }
    
    public static void fillDrivers(Map<Integer, Driver> driverStorage, Map<Integer, Category> categoryStorage) {
        
        if (!driverStorage.isEmpty()) return;
        
        driverStorage.put(1, new Driver(1, "Иванов Иван Иванивич", LocalDate.of(1988, Month.MARCH, 11), (byte) 0, 1,
                                        Arrays.asList(new Category[] {categoryStorage.get(1), categoryStorage.get(3)})));
        
        driverStorage.put(2, new Driver(2, "Иванова Анна Григорьевна", LocalDate.of(1990, Month.JULY, 21), (byte) 1, 1,
                                        Arrays.asList(new Category[] {categoryStorage.get(3)})));
        
        driverStorage.put(3, new Driver(3, "Гайтеров Данил Александрович", LocalDate.of(1992, Month.MAY, 4), (byte) 0, 2,
                                        Arrays.asList(new Category[] {categoryStorage.get(3), categoryStorage.get(10)})));
        
        driverStorage.put(4, new Driver(4, "Кунгуров Алексей Дмитриевич", LocalDate.of(1991, Month.MAY, 18), (byte) 0, 2,
                                        Arrays.asList(new Category[] {categoryStorage.get(3)})));
        
        driverStorage.put(5, new Driver(5, "Воронин Иван Алексеевич", LocalDate.of(1985, Month.SEPTEMBER, 21), (byte) 0, 2,
                                        Arrays.asList(new Category[] {categoryStorage.get(1), categoryStorage.get(3)})));
        
        driverStorage.put(6, new Driver(6, "Титов Денис Иванивич", LocalDate.of(1975, Month.APRIL, 28), (byte) 0, 2,
                                        Arrays.asList(new Category[] {categoryStorage.get(3), categoryStorage.get(15)})));
        
        driverStorage.put(7, new Driver(7, "Соловьева Катерина Дмитриевна", LocalDate.of(1979, Month.AUGUST, 16), (byte) 1, 2,
                                        Arrays.asList(new Category[] {categoryStorage.get(3)})));
        
        driverStorage.put(8, new Driver(8, "Иванов Тихон Иванивич", LocalDate.of(1993, Month.MARCH, 7), (byte) 0, 3,
                                        Arrays.asList(new Category[] {categoryStorage.get(1), categoryStorage.get(3)})));
        
        driverStorage.put(9, new Driver(9, "Иванова Диана Олеговна", LocalDate.of(1995, Month.AUGUST, 4), (byte) 1, 3,
                                        Arrays.asList(new Category[] {categoryStorage.get(3)})));
        
    }

    
    public static void fillPolicy(Map<Integer, List<Driver>> policyStorage, Map<Integer, Driver> driverStorage) {
        
        if (!policyStorage.isEmpty()) return;
        
        for (Map.Entry<Integer, Driver> entry : driverStorage.entrySet()) {
            Driver driver = entry.getValue();
            
            if (policyStorage.containsKey(driver.getPolicy())) {
                
                policyStorage.get(driver.getPolicy()).add(driver);
                
            } else {
                
                List<Driver> list = new ArrayList<>();
                list.add(driver);
                policyStorage.put(driver.getPolicy(), list);
                
            }
            
        }
        
    }
}
