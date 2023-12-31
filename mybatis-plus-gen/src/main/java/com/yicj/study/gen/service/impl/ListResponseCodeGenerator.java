package com.yicj.study.gen.service.impl;

import com.yicj.study.gen.service.AbstractCodeGenerator;
import com.yicj.study.gen.service.CodeGenerator;
import com.yicj.study.gen.utils.FreemarkerUtil;


/**
 * @author yicj
 * @date 2023/11/12 20:26
 */
public class ListResponseCodeGenerator extends AbstractCodeGenerator implements CodeGenerator {

    public ListResponseCodeGenerator(String rootPath, String basePackage, String moduleName) {
        super(rootPath, basePackage, moduleName);
    }

    @Override
    protected void loadFtlConfig() throws Exception{
        FreemarkerUtil.initConfig("list-response.java.ftl");
    }

    @Override
    protected String assembleFileName(String entityName) {
        String rootPath = this.getRootPath() ;
        String basePackagePath = this.getBasePackagePath() ;
        String moduleName = this.getModuleName() ;
        return rootPath + "\\src\\main\\java\\" + basePackagePath +"\\"+ moduleName+"\\web\\response\\List"+entityName+"Response.java" ;
    }
}
