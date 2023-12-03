package com.yicj.study.rw.datasource;

import lombok.Getter;

@Getter
public enum DsType {
    MASTER("master"),
    SLAVE("slave");

    private final String code ;

    DsType(String code){
        this.code = code ;
    }
}