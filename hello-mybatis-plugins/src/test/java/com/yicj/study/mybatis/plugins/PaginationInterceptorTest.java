package com.yicj.study.mybatis.plugins;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.yicj.study.mybatis.HelloPluginsApplication;
import com.yicj.study.mybatis.model.vo.PageRowBounds;
import com.yicj.study.mybatis.pages.PagePlugin;
import com.yicj.study.mybatis.repository.entity.UserEntity;
import com.yicj.study.mybatis.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * @author yicj
 * @date 2023/10/14 13:21
 */
@Slf4j
@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaginationInterceptorTest {

    @Autowired
    private UserMapper userMapper ;

    @Test
    public void list4Page2(){
        String name = "王五" ;
        RowBounds rowBounds = new PageRowBounds(1, 2) ;
        List<UserEntity> list = userMapper.list4Page2(name, rowBounds);
        list.forEach(item -> log.info("item -> {}", item));
    }

    //Loading configuration information from the nested Config class
    @Configuration
    @MapperScan("com.yicj.study.mybatis.repository.mapper")
    static class MyConfig{
        //@Bean
        public PaginationInterceptor pagePlugin(){
            return new PaginationInterceptor();
        }
    }
}
