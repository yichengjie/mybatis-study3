package com.yicj.study.user.web.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
* <p>
*  列表查询Response
* </p>
*
* @author yicj
* @since 2023-11-12
*/
@Data
public class ListUserResponse {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("工作")
    private String job;

    private String company;

    @ApiModelProperty("新建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("新建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime modifyTime;

    @ApiModelProperty("更新人")
    private String modifyBy;

}
