package com.example.phishingbackend.controller;

import com.example.phishingbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/user")
@CrossOrigin(origins = "*")
public class AdminUserController {

    @Autowired
    private UserMapper userMapper;

    // 1. 获取所有用户列表
    @GetMapping("/list")
    public List<Map<String, Object>> getUserList() {
        return userMapper.getAllUsers();
    }

    // 2. 封禁/解封 账号状态
    @PutMapping("/status/{id}/{status}")
    public Map<String, Object> changeStatus(@PathVariable Long id, @PathVariable Integer status) {
        userMapper.updateUserStatus(id, status);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", status == 1 ? "解封成功" : "账号已封禁");
        return res;
    }

    // 3. 重置密码为默认密码 (123456)
    @PutMapping("/resetPwd/{id}")
    public Map<String, Object> resetPassword(@PathVariable Long id) {
        userMapper.resetPassword(id, "123456");
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "密码已重置为 123456");
        return res;
    }
}