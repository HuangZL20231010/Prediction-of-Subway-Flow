package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.userMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.userService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class userServiceImpl implements userService {

    @Resource
    private userMapper userMapper;

    @Override
    public boolean insertUser(User user)
    {
        userMapper.insert(user);
        return true;
    }

    @Override
    public Page<User> findPage(Integer pageNum, Integer pageSize, String search) {
        return userMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<User>lambdaQuery().like(User::getName, search));
    }

    @Override
    public boolean deleteUserById(Long id) {
        userMapper.deleteById(id);
        return true;
    }
}
