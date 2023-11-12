package com.yicj.study.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yicj.study.common.model.vo.PageVO;

import java.util.List;
import java.util.function.Function;

/**
 * @author yicj
 * @date 2023/11/12 16:09
 */
public class CommonUtils {

    public static <T, R> PageVO<R> toPageVO(IPage<T> page, Function<T, R> func){
        PageVO<R> pageVO = new PageVO<>();
        page.getRecords() ;
        List<R> retList = listMapping(page.getRecords(), func);
        pageVO.setList(retList);
        pageVO.setCount(page.getTotal());
        return pageVO ;
    }

    public static <T, R> List<R> listMapping(List<T> list, Function<T, R> func){
        return list.stream().map(func).toList() ;
    }

    public static <T, R> R copyObject(T obj, R target){
        BeanUtil.copyProperties(obj, target, true);
        return target ;
    }

}
