package com.yicj.study.gen.service.impl;

import com.yicj.study.gen.service.CodeGenerator;
import com.yicj.study.gen.utils.FreemarkerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yicj
 * @date 2023/11/12 20:26
 */
public class ListResponseCodeGenerator implements CodeGenerator {

    @Override
    public void execute(String rootPath, String moduleName, String entityName) throws Exception {
        FreemarkerUtil.initConfig("list-response.java.ftl");
        Map<String, Object> param = new HashMap<>() ;
        param.put("entity", entityName) ;
        param.put("module", moduleName) ;
        //
        String fileName =  rootPath + "\\src\\main\\java\\com\\yicj\\study\\"+moduleName+"\\web\\response\\List"+entityName+"Response.java" ;
        FreemarkerUtil.generator(fileName, param);
    }
}
