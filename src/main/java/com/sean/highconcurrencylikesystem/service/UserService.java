package com.sean.highconcurrencylikesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.highconcurrencylikesystem.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    User getLoginUser(HttpServletRequest request);
}
