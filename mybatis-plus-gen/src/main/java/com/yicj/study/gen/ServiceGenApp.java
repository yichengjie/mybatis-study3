package com.yicj.study.gen;

import com.yicj.study.gen.utils.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yicj
 * @date 2023/11/12 17:35
 */
public class ServiceGenApp {

    public static void main(String[] args) throws Exception {
        FreemarkerUtil.initConfig("service.java.ftl");
        Map<String, Object> param = new HashMap<>() ;
        param.put("entity", "User") ;
        //
        String fileName = "D:\\code\\study\\mybatis-study3\\order-service\\src\\main\\java\\com\\yicj\\study\\order\\service\\IUserService.java" ;
        FreemarkerUtil.generator(fileName, param);
    }

}
