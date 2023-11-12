package com.yicj.study.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yicj.study.common.model.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

/**
 * @author yicj
 * @date 2023/11/12 16:09
 */

public class CommonUtils {
    private static Logger log = LoggerFactory.getLogger(CommonUtils.class) ;
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

    public static <T> void listPrint(List<T> list){
        if (list == null || list.isEmpty()){
            log.info("list is empty !");
            return;
        }
        listPrint(list, list.size());
    }

    public static <T> void listPrint(List<T> list, int count){
        if (list == null || list.isEmpty()){
            log.info("list is empty !");
            return;
        }
        log.info("list size : {}", list.size());
        list.stream().limit(count)
            .forEach(item -> log.info("===> : {}", item));
    }


    public static <T> void pagePrint(PageVO<T> pageVO){
        if (pageVO == null || pageVO.getList() == null || pageVO.getList().isEmpty()){
            log.info("page is empty !");
            return;
        }
        log.info("page count : {}", pageVO.getCount());
        pageVO.getList().stream()
            .forEach(item -> log.info("===> : {}", item));
    }
}
