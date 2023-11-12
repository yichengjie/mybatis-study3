package com.yicj.study.common.model.vo;

import com.yicj.study.common.constants.CommonConstants;

/**
 * @author yicj
 * @date 2023/11/12 15:57
 */
public class ResponseVO<T>{

    private String code ;

    private String message ;

    private T data ;

    public static <T> ResponseVO<T> success(T data){
        ResponseVO<T> vo = new ResponseVO<T>() ;
        vo.setCode(CommonConstants.REST_SUCCESS_CODE);
        vo.setData(data);
        return vo ;
    }

    public static <T> ResponseVO<T> error(String code, String message){
        ResponseVO<T> vo = new ResponseVO<T>() ;
        vo.setCode(code);
        vo.setMessage(message);
        return vo ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
