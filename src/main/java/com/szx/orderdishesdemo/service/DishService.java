package com.szx.orderdishesdemo.service;


import com.szx.orderdishesdemo.mapper.DishMapper;
import com.szx.orderdishesdemo.model.Dish;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：XiaoXing
 * @ Date：2025-03-11 09:36
 * @ Description：
 */
public interface DishService {

    List<Dish> getAllDishes();

    Map<String, List<Dish>> getDishesGroupedByCategory();

    List<Dish> getDishesByCategory(String category);

    void addDish(String category, String name);

    List<Dish> getRandomTwoDishes(String category);

}
