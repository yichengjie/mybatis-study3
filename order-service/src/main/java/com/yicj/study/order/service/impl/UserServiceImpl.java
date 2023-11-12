package com.yicj.study.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yicj.study.common.enums.DeleteFlagEnums;
import com.yicj.study.common.model.dto.IdentityDTO;
import com.yicj.study.common.model.vo.PageVO;
import com.yicj.study.common.utils.CommonUtils;
import com.yicj.study.order.repository.entity.User;
import com.yicj.study.order.repository.mapper.UserMapper;
import com.yicj.study.order.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.order.web.request.ListUserRequest;
import com.yicj.study.order.web.request.SaveUserRequest;
import com.yicj.study.order.web.response.ListUserResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

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

    /**
     * 新增操作
     * @param request 新增参数
     * @return 主键id
     */
    @Override
    public Integer addEntity(SaveUserRequest request, IdentityDTO identity) {
        User entity = new User() ;
        CommonUtils.copyObject(request, entity) ;
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateBy(identity.getUserCode());
        entity.setModifyTime(LocalDateTime.now());
        entity.setModifyBy(identity.getUserCode());
        this.save(entity) ;
        return entity.getId();
    }


    @Override
    public Integer updateEntity(SaveUserRequest request, IdentityDTO identity) {
        User entity = new User() ;
        CommonUtils.copyObject(request, entity) ;
        entity.setModifyTime(LocalDateTime.now());
        entity.setModifyBy(identity.getUserCode());
        // todo 这里更新是只更新有值部分？
        return this.baseMapper.updateById(entity) ;
    }

    /**
     * 分页查询操作
     * @param request 查询参数
     * @return 分页数据
     */
    @Override
    public PageVO<ListUserResponse> list4Page(ListUserRequest request){
        IPage<User> pageParam = new Page<>() ;
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        IPage<User> page = this.page(pageParam, wrapper);
        return CommonUtils.toPageVO(
                page, item -> CommonUtils.copyObject(item, new ListUserResponse())) ;
    }

    @Override
    public Integer deleteEntity(Integer id, IdentityDTO identity) {
        User entity = new User() ;
        entity.setId(id);
        //
        entity.setDelFlag(DeleteFlagEnums.DELETED.getCode());
        entity.setModifyTime(LocalDateTime.now());
        entity.setModifyBy(identity.getUserCode());
        return baseMapper.updateById(entity) ;
    }

}