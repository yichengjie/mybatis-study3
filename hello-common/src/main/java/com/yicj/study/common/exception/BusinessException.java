package com.yicj.study.common.exception;

/**
 * @author yicj
 * @date 2023/11/12 16:02
 */
public class BusinessException extends RuntimeException {

    private String code ;

    private String message ;

    public BusinessException(String code, String message){
        super(message);
        this.code = code ;
        this.message = message ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
