package com.yicj.study.rw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yicj
 * @date 2023/11/12 20:51
 */
@SpringBootApplication
@MapperScan("com.yicj.study.rw.repository.mapper")
public class HelloReadWriteApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloReadWriteApplication.class, args) ;
    }
}
