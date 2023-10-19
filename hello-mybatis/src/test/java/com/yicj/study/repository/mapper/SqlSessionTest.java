package com.yicj.study.repository.mapper;

import com.yicj.study.HelloMybatisApplication;
import com.yicj.study.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yicj
 * @date 2023/10/19 21:50
 */
@Slf4j
@Transactional
@SpringBootTest(classes = HelloMybatisApplication.class)
public class SqlSessionTest {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate ;

    @Test
    public void insert(){
        String statementId = "com.yicj.study.repository.mapper.UserMapper.insert" ;
        UserEntity entity = new UserEntity() ;
        entity.setName("name-test") ;
        entity.setJob("job-test") ;
        entity.setCompany("company-test") ;
        int count = sqlSessionTemplate.insert(statementId, entity);
        log.info("count : {}", count);
    }
}
