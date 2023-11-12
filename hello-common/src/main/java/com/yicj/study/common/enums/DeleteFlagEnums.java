package com.yicj.study.common.enums;

/**
 * @author yicj
 * @date 2023/11/12 16:53
 */
public enum DeleteFlagEnums {
    //1：删除，0：未删除
    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除"),
    ;

    private Integer code ;

    private String name ;

    DeleteFlagEnums(Integer code, String name){
        this.code = code ;
        this.name = name ;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
