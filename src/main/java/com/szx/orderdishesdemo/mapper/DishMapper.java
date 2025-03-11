package com.szx.orderdishesdemo.mapper;


import com.szx.orderdishesdemo.model.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：XiaoXing
 * @ Date：2025-03-11 09:36
 * @ Description：
 */
@Mapper
public interface DishMapper {

    int createTable();

    // 查询所有菜品
    List<Dish> selectAllDishes();

    // 根据分类查询菜品
    List<Dish> selectDishesByCategory(String category);

    // 插入菜品
    int insertDish(Dish dish);
}
