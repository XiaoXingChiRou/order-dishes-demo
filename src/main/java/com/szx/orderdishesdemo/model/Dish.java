package com.szx.orderdishesdemo.model;


import lombok.Data;
import lombok.ToString;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：XiaoXing
 * @ Date：2025-03-11 09:36
 * @ Description：
 */
@Data
@ToString
public class Dish {
    private Integer id;
    private String category;
    private String name;
}
