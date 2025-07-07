package com.sean.highconcurrencylikesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.highconcurrencylikesystem.model.entity.User;
import com.sean.highconcurrencylikesystem.service.UserService;
import com.sean.highconcurrencylikesystem.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




