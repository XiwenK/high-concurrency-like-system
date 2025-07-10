package com.sean.highconcurrencylikesystem.service;

import com.sean.highconcurrencylikesystem.model.dto.DoThumbRequest;
import com.sean.highconcurrencylikesystem.model.entity.Thumb;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

public interface ThumbService extends IService<Thumb> {

    Boolean doThumb(DoThumbRequest doThumbRequest, HttpServletRequest request);

    Boolean undoThumb(DoThumbRequest doThumbRequest, HttpServletRequest request);

}
