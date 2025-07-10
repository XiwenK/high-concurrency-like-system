package com.sean.highconcurrencylikesystem.service;

import com.sean.highconcurrencylikesystem.model.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.highconcurrencylikesystem.model.vo.BlogVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BlogService extends IService<Blog> {

    BlogVO getBlogVOById(long blogId, HttpServletRequest request);

    List<BlogVO> getBlogVOList(List<Blog> blogList, HttpServletRequest request);

}
