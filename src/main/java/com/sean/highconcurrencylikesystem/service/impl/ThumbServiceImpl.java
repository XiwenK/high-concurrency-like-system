package com.sean.highconcurrencylikesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.highconcurrencylikesystem.constant.ThumbConstant;
import com.sean.highconcurrencylikesystem.mapper.ThumbMapper;
import com.sean.highconcurrencylikesystem.model.dto.DoThumbRequest;
import com.sean.highconcurrencylikesystem.model.entity.Blog;
import com.sean.highconcurrencylikesystem.model.entity.Thumb;
import com.sean.highconcurrencylikesystem.model.entity.User;
import com.sean.highconcurrencylikesystem.service.BlogService;
import com.sean.highconcurrencylikesystem.service.ThumbService;
import com.sean.highconcurrencylikesystem.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb>
        implements ThumbService {

    @Resource
    private UserService userService;

    @Resource
    private BlogService blogService;

    private final TransactionTemplate transactionTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Boolean doThumb(DoThumbRequest doThumbRequest, HttpServletRequest request) {
        if (doThumbRequest == null || doThumbRequest.getBlogId() == null) {
            throw new RuntimeException("Invalid request parameter");
        }

        User loginUser = userService.getLoginUser(request);

        // Add program lock for doThumb transaction
        synchronized (loginUser.getId().toString().intern()) {
            // Programmatic transaction
            return transactionTemplate.execute(status -> {
                Long blogId = doThumbRequest.getBlogId();

                /* check hasThumb from db record
                boolean exists = this.lambdaQuery()
                        .eq(Thumb::getUserId, loginUser.getId())
                        .eq(Thumb::getBlogId, blogId)
                        .exists();
                 */

                Boolean exists = this.hasThumb(blogId, loginUser.getId());
                if (exists) {
                    throw new RuntimeException("User already did thumb");
                }

                boolean update = blogService.lambdaUpdate()
                        .eq(Blog::getId, blogId)
                        .setSql("thumbCount = thumbCount + 1")
                        .update();

                Thumb thumb = new Thumb();
                thumb.setUserId(loginUser.getId());
                thumb.setBlogId(blogId);

                // Only successful update then do save and update redis cache

                // return update && this.save(thumb);
                boolean success = update && this.save(thumb);
                if (success) {
                    redisTemplate.opsForHash()
                            .put(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId().toString(),
                                    blogId.toString(), thumb.getId());
                }

                return success;
            });
        }
    }

    @Override
    public Boolean undoThumb(DoThumbRequest doThumbRequest, HttpServletRequest request) {
        if (doThumbRequest == null || doThumbRequest.getBlogId() == null) {
            throw new RuntimeException("Invalid request parameter");
        }

        User loginUser = userService.getLoginUser(request);

        // Add program lock for doThumb transaction
        synchronized (loginUser.getId().toString().intern()) {
            return transactionTemplate.execute(status -> {
                Long blogId = doThumbRequest.getBlogId();

                /* check thumb record from db
                Thumb thumb = this.lambdaQuery()
                        .eq(Thumb::getUserId, loginUser.getId())
                        .eq(Thumb::getBlogId, blogId)
                        .one();

                if (thumb == null) throw new RuntimeException("User has not thumbed");
                 */

                Object thumbIdObj = redisTemplate.opsForHash()
                        .get(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId().toString(),
                                blogId.toString());
                if (thumbIdObj == null) {
                    throw new RuntimeException("User has not thumbed");
                }
                Long thumbId = Long.valueOf(thumbIdObj.toString());

                boolean update = blogService.lambdaUpdate()
                        .eq(Blog::getId, blogId)
                        .setSql("thumbCount = thumbCount - 1")
                        .update();

                // return update && this.removeById(thumb.getId());
                boolean success = update && this.removeById(thumbId);
                if (success) {
                    redisTemplate.opsForHash()
                            .delete(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId(),
                                    blogId.toString());
                }

                return success;
            });
        }
    }

    @Override
    public Boolean hasThumb(Long blogId, Long userId) {
        return redisTemplate.opsForHash()
                .hasKey(ThumbConstant.USER_THUMB_KEY_PREFIX + userId, blogId.toString());
    }

}




