package com.yicj.study.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yicj.study.common.model.dto.IdentityDTO;
import com.yicj.study.common.model.vo.PageVO;
import com.yicj.study.user.repository.entity.User;
import com.yicj.study.user.web.request.PageUserRequest;
import com.yicj.study.user.web.request.SaveUserRequest;
import com.yicj.study.user.web.response.ListUserResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yicj
 * @since 2023-11-12
 */
public interface IUserService extends IService<User> {

    Integer addEntity(SaveUserRequest request, IdentityDTO identity);

    Integer updateEntity(SaveUserRequest request, IdentityDTO identity);

    PageVO<ListUserResponse> list4Page(PageUserRequest request);

    Integer deleteEntity(Integer id, IdentityDTO identity);
}