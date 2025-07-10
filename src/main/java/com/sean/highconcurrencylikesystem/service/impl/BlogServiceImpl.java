package com.sean.highconcurrencylikesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.highconcurrencylikesystem.model.entity.Blog;
import com.sean.highconcurrencylikesystem.model.entity.Thumb;
import com.sean.highconcurrencylikesystem.model.entity.User;
import com.sean.highconcurrencylikesystem.model.vo.BlogVO;
import com.sean.highconcurrencylikesystem.service.BlogService;
import com.sean.highconcurrencylikesystem.mapper.BlogMapper;
import com.sean.highconcurrencylikesystem.service.ThumbService;
import com.sean.highconcurrencylikesystem.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
        implements BlogService {

    @Resource
    private UserService userService;

    /**
     * "@Lazy" is temporarily used to resolve circular reference, the best way
     * is to redesign the service, importing a new service to handle thumbCount update.
     */
    @Resource
    @Lazy
    private ThumbService thumbService;

    @Override
    public BlogVO getBlogVOById(long blogId, HttpServletRequest request) {
        Blog blog = this.getById(blogId);
        User loginUser = userService.getLoginUser(request);

        return this.getBlogVO(blog, loginUser);
    }

    private BlogVO getBlogVO(Blog blog, User loginUser) {
        BlogVO blogVO = new BlogVO();
        BeanUtil.copyProperties(blog, blogVO);

        if (loginUser == null) {
            return blogVO;
        }

        Thumb thumb = thumbService.lambdaQuery()
                .eq(Thumb::getUserId, loginUser.getId())
                .eq(Thumb::getBlogId, blog.getId())
                .one();
        blogVO.setHasThumb(thumb != null);

        return blogVO;
    }

    @Override
    public List<BlogVO> getBlogVOList(List<Blog> blogList, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);

        Map<Long, Boolean> blogIdHasThumbMap = new HashMap<>();
        if (ObjUtil.isNotEmpty(loginUser)) {
            Set<Long> blogIdSet = blogList.stream()
                    .map(Blog::getId)
                    .collect(Collectors.toSet());

            // Get thumbs done by current user and in the query blogId list
            List<Thumb> thumbList = thumbService.lambdaQuery()
                    .eq(Thumb::getUserId, loginUser.getId())
                    .in(Thumb::getBlogId, blogIdSet)
                    .list();

            thumbList.forEach(blogThumb -> blogIdHasThumbMap.put(blogThumb.getBlogId(), true));
        }

        return blogList.stream()
                .map(blog -> {
                    BlogVO blogVO = BeanUtil.copyProperties(blog, BlogVO.class);
                    blogVO.setHasThumb(blogIdHasThumbMap.get(blog.getId()));
                    return blogVO;
                })
                .collect(Collectors.toList());
    }

}




