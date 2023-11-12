package com.yicj.study.gen.service.impl;

import com.yicj.study.gen.service.AbstractCodeGenerator;
import com.yicj.study.gen.service.CodeGenerator;
import com.yicj.study.gen.utils.FreemarkerUtil;


/**
 * @author yicj
 * @date 2023/11/12 20:26
 */
public class PageRequestCodeGenerator extends AbstractCodeGenerator implements CodeGenerator {
    public PageRequestCodeGenerator(String rootPath, String basePackage, String moduleName) {
        super(rootPath, basePackage, moduleName);
    }

    @Override
    protected void loadFtlConfig() throws Exception {
        FreemarkerUtil.initConfig("page-request.java.ftl");
    }

    @Override
    protected String assembleFileName(String entityName) {
        String moduleName = this.getModuleName() ;
        String rootPath = this.getRootPath() ;
        String basePackagePath = this.getBasePackagePath() ;
        return rootPath + "\\src\\main\\java\\" + basePackagePath +"\\"+ moduleName+"\\web\\request\\Page"+entityName+"Request.java" ;
    }
}
