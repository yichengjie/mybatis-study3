package com.yicj.study.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yicj
 * @date 2023年10月10日 18:36
 */

@SpringBootApplication
@MapperScan("com.yicj.study.mybatis.repository.mapper")
public class HelloPluginsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloPluginsApplication.class, args) ;
    }
}
