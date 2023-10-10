package com.yicj.study.mybatis.repository.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yicj.study.mybatis.HelloPluginsApplication;
import com.yicj.study.mybatis.model.vo.PageRowBounds;
import com.yicj.study.mybatis.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Slf4j
@SpringBootTest(classes = HelloPluginsApplication.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper ;

    @Test
    void list4Page(){
        Page<UserEntity> pageParam = new Page<>(1,3) ;
        Page<UserEntity> pageResult = userMapper.selectPage(pageParam, null);
        List<UserEntity> records = pageResult.getRecords();
        records.forEach(item -> log.info("item -> {}", item));
    }

    @Test
    void list4Page2(){
        RowBounds rowBounds = new PageRowBounds(0, 2) ;
        List<UserEntity> list = userMapper.list4Page(rowBounds);
        list.forEach(item -> log.info("item -> {}", item));
        SqlSession sqlSession = null ;
        sqlSession.getMapper(UserMapper.class) ;
    }
}