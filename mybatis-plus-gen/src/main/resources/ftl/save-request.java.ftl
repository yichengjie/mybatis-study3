package com.yicj.study.${module}.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author yicj
 * @date 2023/11/12 16:17
 */
@Data
public class Save${entity}Request {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("工作")
    private String job;

    private String company;

}