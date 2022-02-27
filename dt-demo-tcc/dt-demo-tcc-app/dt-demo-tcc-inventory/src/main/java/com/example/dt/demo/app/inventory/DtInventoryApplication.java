package com.example.dt.demo.app.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.example.dt.demo.core.inventory.repository")
public class DtInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtInventoryApplication.class, args);
    }

}
