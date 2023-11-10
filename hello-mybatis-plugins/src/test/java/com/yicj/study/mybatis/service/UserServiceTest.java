package com.yicj.study.mybatis.service;

import com.yicj.study.mybatis.HelloPluginsApplication;
import com.yicj.study.mybatis.pages.PageInfo;
import com.yicj.study.mybatis.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author yicj
 * @date 2023/11/10 8:55
 */
@Slf4j
@SpringBootTest(classes = HelloPluginsApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService ;

    @Test
    public void listUser(){
        UserEntity entity = new UserEntity() ;
        List<UserEntity> list = userService.listUser(entity);
        log.info("list size : {}", list.size());
        list.forEach(item -> log.info("item : {}", item));
    }

    @Test
    public void list4Page(){
        PageInfo pageInfo = new PageInfo() ;
        pageInfo.setCurrentPage(1);
        pageInfo.setShowCount(2);
        List<UserEntity> list = userService.list4Page("张三", pageInfo);
        list = userService.list4Page("张三", pageInfo);
        list = userService.list4Page("张三", pageInfo);
        list = userService.list4Page("张三", pageInfo);
        list = userService.list4Page("张三", pageInfo);
        log.info("list size : {}", list.size());
        list.forEach(item -> log.info("item : {}", item));
    }
}
