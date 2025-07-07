package com.sean.highconcurrencylikesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.highconcurrencylikesystem.model.entity.Thumb;
import com.sean.highconcurrencylikesystem.service.ThumbService;
import com.sean.highconcurrencylikesystem.mapper.ThumbMapper;
import org.springframework.stereotype.Service;

@Service
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb>
    implements ThumbService{

}




