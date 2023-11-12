package com.yicj.study.order.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yicj
 * @date 2023/11/12 15:53
 */
@Data
public class ListUserRequest {

    @ApiModelProperty("姓名")
    private String name ;

}
