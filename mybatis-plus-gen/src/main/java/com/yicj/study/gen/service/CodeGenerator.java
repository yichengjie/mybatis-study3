package com.yicj.study.gen.service;

/**
 * @author yicj
 * @date 2023/11/12 20:23
 */
public interface CodeGenerator {

    void execute(String rootPath, String moduleName, String entityName) throws Exception;
}
