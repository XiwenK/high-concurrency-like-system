package com.sean.highconcurrencylikesystem.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "blog")
@Data
public class Blog {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private String title;

    private String coverImg;

    private String content;

    private Integer thumbCount;

    private Date createTime;

    private Date updateTime;

}