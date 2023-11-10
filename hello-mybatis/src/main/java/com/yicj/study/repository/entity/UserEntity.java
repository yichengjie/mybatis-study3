package com.yicj.study.repository.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: yicj
 * @date: 2023/8/11 8:58
 */
@Accessors(chain = true)
@TableName("t_user")
public class UserEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id ;

    private String name ;

    private String job ;

//    @FieldEncrypt
    private String company ;

    @TableLogic
    private Integer delFlag ;

    /**
     * 创建时间
     */
    // 注意！这里需要标记为填充字段
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime ;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy ;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime ;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifyBy ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
