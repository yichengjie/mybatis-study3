package com.yicj.study.order.web.request;

import com.yicj.study.common.model.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yicj
 * @date 2023/11/12 15:53
 */
@Data
public class PageUserRequest extends PageRequest {

    @ApiModelProperty("姓名")
    private String name ;

}
