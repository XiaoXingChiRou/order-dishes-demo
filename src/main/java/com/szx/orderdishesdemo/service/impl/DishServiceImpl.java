package com.szx.orderdishesdemo.service.impl;


import com.szx.orderdishesdemo.config.DataLoad;
import com.szx.orderdishesdemo.mapper.DishMapper;
import com.szx.orderdishesdemo.model.Dish;
import com.szx.orderdishesdemo.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：XiaoXing
 * @ Date：2025-03-11 09:50
 * @ Description：
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DataLoad dataLoad;

    private final Random random = new Random();


    @PostConstruct
    public void init() {
        dishMapper.createTable();
        // 若数据库中无数据，则插入默认菜品
        if (dishMapper.selectAllDishes().isEmpty()) {
            dataLoad.insertIntoSql();
        }
    }

    @Override
    public List<Dish> getAllDishes() {
        return dishMapper.selectAllDishes();
    }

    @Override
    public Map<String, List<Dish>> getDishesGroupedByCategory() {
        List<Dish> dishes = getAllDishes();
        Map<String, List<Dish>> map = new HashMap<>();
        for (Dish dish : dishes) {
            map.computeIfAbsent(dish.getCategory(), k -> new ArrayList<>()).add(dish);
        }
        return map;
    }

    @Override
    public List<Dish> getDishesByCategory(String category) {
        return dishMapper.selectDishesByCategory(category);
    }

    @Override
    public void addDish(String category, String name) {
        Dish dish = new Dish();
        dish.setCategory(category);
        dish.setName(name);
        dishMapper.insertDish(dish);
    }

    @Override
    public List<Dish> getRandomTwoDishes(String category) {
        List<Dish> dishes = getDishesByCategory(category);
        if (dishes.size() < 2) return Collections.emptyList();
        int index1 = random.nextInt(dishes.size());
        int index2;
        do {
            index2 = random.nextInt(dishes.size());
        } while (index1 == index2);
        return Arrays.asList(dishes.get(index1), dishes.get(index2));
    }
}
