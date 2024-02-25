package com.chinocarbon.judgement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.chinocarbon.judgement.dao")
@SpringBootApplication
@ServletComponentScan(basePackages = "com.chinocarbon")
public class ModuleJudgementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleJudgementApplication.class, args);
    }

}
