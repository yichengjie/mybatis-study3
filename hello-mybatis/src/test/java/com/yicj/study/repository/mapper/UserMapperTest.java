package com.yicj.study.repository.mapper;

import com.yicj.study.HelloMybatisApplication;
import com.yicj.study.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest(classes = HelloMybatisApplication.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper ;

    @Test
    public void insert(){
        UserEntity entity = new UserEntity() ;
        entity.setName("name-test") ;
        entity.setJob("job-test") ;
        entity.setCompany("company-test") ;
        userMapper.insert(entity) ;
        log.info("entity : {}", entity);
    }

}