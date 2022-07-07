package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id)
    {
        if (userService.deleteUserById(id))
            return Result.success();
        else
            return Result.error("1", "delete error");
    }

    @PostMapping("/test/{i}")
    public Result<?> test(@PathVariable("i") int i)
    {
        System.out.println(i);
        return Result.success();
    }

    @PostMapping("/test")
    public Result<?> login(@RequestBody int i){
        if(i==1){
            return Result.error("-1","收到数据！");
        }
        return Result.success();
    }

}
