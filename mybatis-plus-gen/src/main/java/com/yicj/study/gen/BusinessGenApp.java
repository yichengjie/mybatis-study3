package com.yicj.study.gen;

import com.yicj.study.gen.service.CodeGenerator;
import com.yicj.study.gen.service.impl.*;

/**
 * @author yicj
 * @date 2023/11/12 20:28
 */
public class BusinessGenApp {
    private static final String rootPath = "D:\\code\\study\\mybatis-study3\\user-provider" ;

    public static void main(String[] args) throws Exception {
        String basePackage = "com.yicj.study" ;
        String moduleName = "user" ;
        String entityName = "User" ;
        // 初始化
        CodeGenerator responseGen = new ListResponseCodeGenerator(rootPath, basePackage, moduleName) ;
        CodeGenerator pageReqGen = new PageRequestCodeGenerator(rootPath, basePackage, moduleName) ;
        CodeGenerator saveGen = new SaveRequestCodeGenerator(rootPath, basePackage, moduleName);
        CodeGenerator serviceGen = new ServiceCodeGenerator(rootPath, basePackage, moduleName) ;
        CodeGenerator serviceImplGen = new ServiceImplCodeGenerator(rootPath, basePackage, moduleName);
        // 执行
        responseGen.execute(entityName);
        pageReqGen.execute(entityName);
        saveGen.execute(entityName);
        //serviceGen.execute(entityName);
        //serviceImplGen.execute(entityName);

    }
}
