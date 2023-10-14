package com.yicj.study.mybatis.plugins;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.yicj.study.mybatis.model.vo.PageRowBounds;
import com.yicj.study.mybatis.repository.entity.UserEntity;
import com.yicj.study.mybatis.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yicj
 * @date 2023/10/14 15:18
 */
@Slf4j
@Transactional
@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MybatisPlusInterceptorTest {

    @Autowired
    private UserMapper userMapper ;


    @Test
    public void list4Page2(){
        String name = "王五" ;
        IPage<UserEntity> pageParam = new Page<>(1, 2) ;
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>() ;
        queryWrapper.like(UserEntity::getName, name) ;
        IPage<UserEntity> page = userMapper.selectPage(pageParam, queryWrapper);
        page.getRecords().forEach(item -> log.info("item -> {}", item));
    }


    @Configuration
    @MapperScan("com.yicj.study.mybatis.repository.mapper")
    static class MyConfig{
        //@Bean
        public MybatisPlusInterceptor pagePlugin(){
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//如果配置多个插件,切记分页最后添加
            //interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
            return interceptor;
        }
    }

}
