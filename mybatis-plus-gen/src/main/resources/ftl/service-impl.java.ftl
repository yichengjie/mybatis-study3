package com.yicj.study.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yicj.study.common.enums.DeleteFlagEnums;
import com.yicj.study.common.model.dto.IdentityDTO;
import com.yicj.study.common.model.vo.PageVO;
import com.yicj.study.common.utils.CommonUtils;
import com.yicj.study.order.repository.entity.${entity};
import com.yicj.study.order.repository.mapper.${entity}Mapper;
import com.yicj.study.order.service.I${entity}Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.order.web.request.Page${entity}Request;
import com.yicj.study.order.web.request.Save${entity}Request;
import com.yicj.study.order.web.response.List${entity}Response;
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
public class ${entity}ServiceImpl extends ServiceImpl<${entity}Mapper, ${entity}> implements I${entity}Service {

    /**
     * 新增操作
     * @param request 新增参数
     * @return 主键id
     */
    @Override
    public Integer addEntity(Save${entity}Request request, IdentityDTO identity) {
        ${entity} entity = new ${entity}() ;
        CommonUtils.copyObject(request, entity) ;
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateBy(identity.getUserCode());
        entity.setModifyTime(LocalDateTime.now());
        entity.setModifyBy(identity.getUserCode());
        this.save(entity) ;
        return entity.getId();
    }


    @Override
    public Integer updateEntity(Save${entity}Request request, IdentityDTO identity) {
        ${entity} entity = new ${entity}() ;
        CommonUtils.copyObject(request, entity) ;
        entity.setModifyTime(LocalDateTime.now());
        entity.setModifyBy(identity.getUserCode());
        // 验证发现mybatis只更新有值的字段，对于空字段不会更新
        return this.baseMapper.updateById(entity) ;
    }

    /**
     * 分页查询操作
     * @param request 查询参数
     * @return 分页数据
     */
    @Override
    public PageVO<List${entity}Response> list4Page(Page${entity}Request request){
        IPage<${entity}> pageParam =
                new Page<>(request.getCurrentPage(), request.getPageSize()) ;
        LambdaQueryWrapper<${entity}> wrapper = new LambdaQueryWrapper<>();
        IPage<${entity}> page = this.page(pageParam, wrapper);
        return CommonUtils.toPageVO(
                page, item -> CommonUtils.copyObject(item, new List${entity}Response())) ;
    }

    @Override
    public Integer deleteEntity(Integer id, IdentityDTO identity) {
        ${entity} entity = new ${entity}() ;
        entity.setId(id);
        //
        entity.setDelFlag(DeleteFlagEnums.DELETED.getCode());
        entity.setModifyTime(LocalDateTime.now());
        entity.setModifyBy(identity.getUserCode());
        return baseMapper.updateById(entity) ;
    }

}