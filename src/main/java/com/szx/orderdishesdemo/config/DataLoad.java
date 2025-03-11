package com.szx.orderdishesdemo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：XiaoXing
 * @ Date：2025-03-11 11:16
 * @ Description：
 */
@Component
public class DataLoad {

    private final String sqlFilePath = "data.sql";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertIntoSql() {
        try {
            // 读取 SQL 文件内容
            String sql = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(sqlFilePath).toURI())));
            // 执行 SQL 语句
            jdbcTemplate.execute(sql);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
