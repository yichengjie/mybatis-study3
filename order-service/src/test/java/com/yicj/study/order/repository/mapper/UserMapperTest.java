package com.yicj.study.order.repository.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.yicj.study.order.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;


@Slf4j
@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper ;

    @Test
    public void hello(){
        QueryWrapper<User> wrapper = new QueryWrapper<User>() ;
        List<User> list = userMapper.selectList(wrapper);
        log.info("list size : {}", list.size());
        list.forEach(item -> log.info("item : {}", item));
    }
}