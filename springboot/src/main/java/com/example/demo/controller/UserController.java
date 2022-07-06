package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    userService userService;

    @PostMapping
    public Result<?> insert(@RequestBody User user)
    {
        if (userService.insertUser(user))
            return Result.success();
        else
            return Result.error("1", "insert error");
    }

    @GetMapping
    public Result<?> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String search)
    {
        Page<User> page = userService.findPage(pageNum, pageSize, search);
        return Result.success(page);
    }

    @DeleteMapping("/test/{id}")
    public Result<?> delete(@PathVariable Long id)
    {
        if (userService.deleteUserById(id))
            return Result.success();
        else
            return Result.error("1", "delete error");
    }

    @GetMapping("/{i}")
    public Result<?> test(@PathVariable("i") Integer i)
    {
        return Result.success();
    }

}
