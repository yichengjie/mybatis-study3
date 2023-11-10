package com.yicj.study.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.mybatis.pages.PageInfo;
import com.yicj.study.mybatis.repository.entity.UserEntity;
import com.yicj.study.mybatis.repository.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author yicj
 * @date 2023/11/10 8:50
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserEntity>{
    @Autowired
    private UserMapper userMapper ;

    //1. 这里添加@Transactional 注解
    @Transactional(rollbackFor = Exception.class)
    public List<UserEntity> listUser(UserEntity entity){
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>(entity) ;
        //2. 首次查询
        List<UserEntity> list = baseMapper.selectList(wrapper);
        return list ;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<UserEntity> list4Page(String name, PageInfo pageInfo){
        List<UserEntity> list = baseMapper.list4Page(name, pageInfo);
        return list ;
    }
}
