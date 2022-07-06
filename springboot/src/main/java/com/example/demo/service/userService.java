package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface userService {

    // 插入用户
    boolean insertUser(User user);

    // 分页查询
    Page<User> findPage(Integer pageNum, Integer pageSize, String search);

    // 根据id删除用户
    boolean deleteUserById(Long id);
}
