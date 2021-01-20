package com.jiubo.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@MapperScan("com.jiubo.project.dao")
//开启事务管理
@EnableTransactionManagement
@SpringBootApplication
public class JbxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JbxyApplication.class, args);
    }
}

