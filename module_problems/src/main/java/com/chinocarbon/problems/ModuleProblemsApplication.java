package com.chinocarbon.problems;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.chinocarbon.problems.dao")
@SpringBootApplication()
@ServletComponentScan(basePackages = "com.chinocarbon")
public class ModuleProblemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleProblemsApplication.class, args);
    }

}
