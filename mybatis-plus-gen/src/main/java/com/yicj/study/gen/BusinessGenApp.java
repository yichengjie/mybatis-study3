package com.yicj.study.gen;

import com.yicj.study.gen.service.CodeGenerator;
import com.yicj.study.gen.service.impl.ListResponseCodeGenerator;
import com.yicj.study.gen.service.impl.PageRequestCodeGenerator;
import com.yicj.study.gen.service.impl.ServiceCodeGenerator;
import com.yicj.study.gen.service.impl.ServiceImplCodeGenerator;

/**
 * @author yicj
 * @date 2023/11/12 20:28
 */
public class BusinessGenApp {
    private static final String rootPath = "D:\\code\\study\\mybatis-study3\\order-service" ;

    public static void main(String[] args) throws Exception {
        String moduleName = "order" ;
        String entityName = "User" ;
        // 初始化
        CodeGenerator listResponseCodeGenerator = new ListResponseCodeGenerator() ;
        CodeGenerator pageRequestCodeGenerator = new PageRequestCodeGenerator() ;
        CodeGenerator serviceCodeGenerator = new ServiceCodeGenerator() ;
        CodeGenerator serviceImplCodeGenerator = new ServiceImplCodeGenerator();
        // 执行
        listResponseCodeGenerator.execute(rootPath, moduleName, entityName);
        pageRequestCodeGenerator.execute(rootPath, moduleName, entityName);
        serviceCodeGenerator.execute(rootPath, moduleName, entityName);
        serviceImplCodeGenerator.execute(rootPath, moduleName, entityName);
    }
}
