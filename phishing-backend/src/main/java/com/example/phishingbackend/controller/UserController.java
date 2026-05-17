package com.example.phishingbackend.controller;

import com.example.phishingbackend.entity.User;
import com.example.phishingbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*") // 允许前端跨域
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        Map<String, Object> result = new HashMap<>();

        // 1. 去数据库找这个用户
        User user = userMapper.selectByUsername(username);

        // 2. 账号不存在
        if (user == null) {
            result.put("code", 400);
            result.put("msg", "用户不存在，请检查账号！");
            return result;
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            result.put("code", 403);
            result.put("msg", "您的账号已被管理员封禁，请联系保卫处解封！");
            return result;
        }
        // 3. 密码错误
        if (!user.getPassword().equals(password)) {
            result.put("code", 400);
            result.put("msg", "密码错误，请重试！");
            return result;
        }

        // 4. 登录成功，生成一个模拟的专属 Token (毕设用 UUID 模拟足够了)
        String token = UUID.randomUUID().toString();

        // 5. 打包用户信息返回给前端 Vue
        result.put("code", 200);
        result.put("msg", "登录成功");

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());
        data.put("role", user.getRole()); // 这个 role 决定了前端能看什么菜单！

        result.put("data", data);

        return result;
    }
}