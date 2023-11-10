package com.yicj.study.mybatis.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yicj.study.mybatis.pages.PageInfo;
import com.yicj.study.mybatis.repository.entity.UserEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/11 9:01
 */
@Mapper
@CacheNamespace
public interface UserMapper extends BaseMapper<UserEntity> {

    Integer insertBatchSomeColumn(@Param("list") List<UserEntity> list) ;

    UserEntity findByUserId(Integer id) ;


    @Select("""
            select * from t_user where 1=1 and name = #{name}
            """)
    List<UserEntity> list4Page(
            @Param("name") String name,
            @Param("page") PageInfo pageInfo
            /*RowBounds rowBounds*/) ;


    @Select("""
            select * from t_user where 1=1 and name = #{name}
            """)
    List<UserEntity> list4Page2(
            @Param("name") String name,
            RowBounds rowBounds) ;

}
