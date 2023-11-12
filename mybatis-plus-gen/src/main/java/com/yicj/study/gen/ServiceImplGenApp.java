package com.yicj.study.gen;

import com.yicj.study.gen.utils.FreemarkerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yicj
 * @date 2023/11/12 18:08
 */
public class ServiceImplGenApp {

    public static void main(String[] args) throws Exception{
        FreemarkerUtil.initConfig("serviceImpl.java.ftl");
        Map<String, Object> param = new HashMap<>() ;
        param.put("entity", "User") ;
        //
        String fileName = "D:\\code\\study\\mybatis-study3\\order-service\\src\\main\\java\\com\\yicj\\study\\order\\service\\impl\\UserServiceImpl.java" ;
        FreemarkerUtil.generator(fileName, param);
    }
}
