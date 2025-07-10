package com.sean.highconcurrencylikesystem.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BlogVO {

    private Long id;

    private String title;

    private String coverImg;

    private String content;

    private Integer thumbCount;

    private Date createTime;

    /**
     * If current user has thumbed this blog
     */
    private Boolean hasThumb;
}