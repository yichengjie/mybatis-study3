package com.yicj.study.common.model.request;

/**
 * @author yicj
 * @date 2023/11/12 18:40
 */
public class PageRequest {

    private Integer currentPage = 1;

    private Integer pageSize = 10;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
