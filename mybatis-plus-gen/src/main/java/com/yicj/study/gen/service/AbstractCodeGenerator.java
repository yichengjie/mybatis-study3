package com.yicj.study.gen.service;

import com.yicj.study.gen.utils.FreemarkerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yicj
 * @date 2023/11/12 20:56
 */
public abstract class AbstractCodeGenerator implements CodeGenerator{

    private String rootPath ;
    private String basePackage ;
    private String moduleName ;

    protected AbstractCodeGenerator(String rootPath, String basePackage, String moduleName){
        this.rootPath = rootPath ;
        this.basePackage = basePackage ;
        this.moduleName = moduleName ;
    }

    @Override
    public void execute(String entityName) throws Exception {
        this.loadFtlConfig();
        String moduleName = this.getModuleName() ;
        Map<String, Object> param = new HashMap<>() ;
        param.put("entity", entityName) ;
        param.put("module", moduleName) ;
        //
        String fileName =  this.assembleFileName(entityName) ;
        FreemarkerUtil.generator(fileName, param);
    }

    protected String getBasePackagePath(){
        return basePackage.replaceAll("\\.", "\\\\") ;
    }

    protected abstract void loadFtlConfig() throws Exception;

    protected abstract String assembleFileName(String entityName) ;

    public String getRootPath() {
        return rootPath;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String getModuleName() {
        return moduleName;
    }
}
