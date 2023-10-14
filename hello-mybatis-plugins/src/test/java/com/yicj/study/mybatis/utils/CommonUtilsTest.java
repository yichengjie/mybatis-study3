package com.yicj.study.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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



    @Test
    public void buildMappedStatement(){
        final String resource = null;
        Configuration configuration = null;
        //
        String id = null;
        SqlSource sqlSource = null ;
        StatementType statementType = null ;
        SqlCommandType sqlCommandType = null;
        Integer fetchSize = null ;
        Integer timeout = null;
        String parameterMap= null;
        Class<?> parameterType = null;
        String resultMap = null ;
        Class<?> resultType = null ;
        ResultSetType resultSetType = null ;
        boolean flushCache = false;
        boolean useCache = false;
        boolean resultOrdered = false;
        KeyGenerator keyGenerator = null;
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null ;
        LanguageDriver lang = null;
        String resultSets = null;
        boolean dirtySelect = false;
//        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, id, sqlSource, sqlCommandType)
//                .resource(resource).fetchSize(fetchSize).timeout(timeout).statementType(statementType)
//                .keyGenerator(keyGenerator).keyProperty(keyProperty).keyColumn(keyColumn).databaseId(databaseId).lang(lang)
//                .resultOrdered(resultOrdered).resultSets(resultSets)
//                .resultMaps(getStatementResultMaps(resultMap, resultType, id)).resultSetType(resultSetType)
//                .flushCacheRequired(flushCache).useCache(useCache).cache(currentCache).dirtySelect(dirtySelect);
//        ParameterMap statementParameterMap = getStatementParameterMap(parameterMap, parameterType, id);
//        if (statementParameterMap != null) {
//            statementBuilder.parameterMap(statementParameterMap);
//        }
//        MappedStatement statement = statementBuilder.build();
    }

}
