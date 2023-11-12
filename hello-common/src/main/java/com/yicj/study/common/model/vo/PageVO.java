package com.yicj.study.common.model.vo;

import java.util.List;

/**
 * @author yicj
 * @date 2023/11/12 16:04
 */
public class PageVO <T> {

    private List<T> list ;

    private Long count ;

    public PageVO(){
    }

    public PageVO(List<T> list, Long count){
        this.list = list ;
        this.count = count ;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
