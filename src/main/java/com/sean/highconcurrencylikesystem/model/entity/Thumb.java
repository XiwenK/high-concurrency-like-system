package com.sean.highconcurrencylikesystem.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@TableName(value ="thumb")
@Data
public class Thumb {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long blogId;

    private Date createTime;
}