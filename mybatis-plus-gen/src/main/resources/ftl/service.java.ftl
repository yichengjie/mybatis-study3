package com.yicj.study.${module}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yicj.study.common.model.dto.IdentityDTO;
import com.yicj.study.common.model.vo.PageVO;
import com.yicj.study.${module}.repository.entity.${entity};
import com.yicj.study.${module}.web.request.Page${entity}Request;
import com.yicj.study.${module}.web.request.Save${entity}Request;
import com.yicj.study.${module}.web.response.List${entity}Response;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yicj
 * @since 2023-11-12
 */
public interface I${entity}Service extends IService<${entity}> {

    Integer addEntity(Save${entity}Request request, IdentityDTO identity);

    Integer updateEntity(Save${entity}Request request, IdentityDTO identity);

    PageVO<List${entity}Response> list4Page(Page${entity}Request request);

    Integer deleteEntity(Integer id, IdentityDTO identity);
}