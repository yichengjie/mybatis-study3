package com.yicj.study.mybatis.exception;

/**
 * @author yicj
 * @date 2023年10月10日 18:44
 */
public class AppException extends RuntimeException {

    public AppException(String message){

        super(message);
    }
}
