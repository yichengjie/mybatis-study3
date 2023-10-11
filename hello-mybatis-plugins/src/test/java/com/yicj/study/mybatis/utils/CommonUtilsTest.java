package com.yicj.study.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author yicj
 * @date 2023/10/11 21:36
 */
@Slf4j
public class CommonUtilsTest {

    @Test
    public void matches(){
        String regex = ".*.list4Page" ;
        String statementId = "com.yicj.study.mapper.UserMapper.list4Page" ;
        statementId = "com.yicj.study.mybatis.repository.mapper.UserMapper.selectList" ;
        log.info("matches : {}", statementId.matches(regex));
        // Pattern.matches(regex, this)
    }
}
