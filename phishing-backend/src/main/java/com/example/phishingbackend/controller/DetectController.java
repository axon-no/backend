package com.example.phishingbackend.controller;

import com.example.phishingbackend.entity.DetectTask;
import com.example.phishingbackend.service.DetectService;
import com.example.phishingbackend.mapper.DetectTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
@CrossOrigin(origins = "*") // 允许前端 Vue 跨域调用
public class DetectController {

    @Autowired
    private DetectService detectService;

    @Autowired
    private DetectTaskMapper taskMapper;

    /**
     * 1. 提交检测任务
     */
    @PostMapping("/submit")
    public DetectTask submitTask(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Integer type = (Integer) params.get("taskType");
        String content = (String) params.get("targetContent");

        // 调用 Service 进行 AI 检测
        return detectService.processDetection(userId, type, content);
    }

    /**
     * 2. 获取用户的历史记录 (给前端左侧栏用)
     */
    @GetMapping("/history/{userId}")
    public List<DetectTask> getHistory(@PathVariable Long userId) {
        // 直接调用 Mapper 查询数据库
        return taskMapper.selectByUserId(userId);
    }
}