package com.szx.orderdishesdemo;

import com.szx.orderdishesdemo.ui.DishSwingUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(value = {"com.szx.orderdishesdemo.mapper"})
public class OrderDishesDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OrderDishesDemoApplication.class, args);
        // 通过 Spring 容器获取 Swing UI Bean，并在 EDT 中启动 Swing 界面
        javax.swing.SwingUtilities.invokeLater(() -> {
            DishSwingUI ui = run.getBean(DishSwingUI.class);
            ui.createAndShowUI();
        });
    }

}
