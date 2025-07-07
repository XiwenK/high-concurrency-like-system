package com.sean.highconcurrencylikesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.highconcurrencylikesystem.model.entity.Blog;
import com.sean.highconcurrencylikesystem.service.BlogService;
import com.sean.highconcurrencylikesystem.mapper.BlogMapper;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




