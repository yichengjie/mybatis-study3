package com.yicj.study.order.service.impl;

import com.yicj.study.order.repository.entity.User;
import com.yicj.study.order.repository.mapper.UserMapper;
import com.yicj.study.order.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yicj
 * @since 2023-11-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
