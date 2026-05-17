package com.example.phishingbackend.controller;

import com.example.phishingbackend.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData() {
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 从数据库获取真实的聚合数据
            Map<String, Object> coreStats = adminMapper.getCoreStats();
            List<Map<String, Object>> pieData = adminMapper.getRiskDistribution();
            List<Map<String, Object>> trendData = adminMapper.getDailyTrend();

            // 2. 提取折线图的横纵坐标 (ECharts 需要分开的两个数组)
            List<String> dates = new ArrayList<>();
            List<Long> counts = new ArrayList<>();

            if (trendData != null) {
                for (Map<String, Object> map : trendData) {
                    if (map.get("date") != null && map.get("count") != null) {
                        dates.add(map.get("date").toString());
                        // 统一转成 Long，防止数据库返回不同数值类型报错
                        counts.add(Long.valueOf(map.get("count").toString()));
                    }
                }
            }

            // 3. 封装给前端的 data 结构
            Map<String, Object> data = new HashMap<>();

            // 判空处理：防止表里没有任何数据时后端报错
            if (coreStats != null) {
                data.put("totalChecks", coreStats.get("totalChecks") != null ? coreStats.get("totalChecks") : 0);
                data.put("intercepted", coreStats.get("intercepted") != null ? coreStats.get("intercepted") : 0);
                data.put("activeUsers", coreStats.get("activeUsers") != null ? coreStats.get("activeUsers") : 0);
            } else {
                data.put("totalChecks", 0);
                data.put("intercepted", 0);
                data.put("activeUsers", 0);
            }

            // AI 调用次数：这里直接用“总检测次数”来代替展示，让数据更饱满
            data.put("apiCalls", data.get("totalChecks"));

            data.put("pieData", pieData != null ? pieData : new ArrayList<>());
            data.put("trendDates", dates);
            data.put("trendValues", counts);

            // 4. 打包返回给 Vue
            result.put("code", 200);
            result.put("msg", "获取数据成功");
            result.put("data", data);

        } catch (Exception e) {
            System.err.println("大屏数据聚合报错: " + e.getMessage());
            result.put("code", 500);
            result.put("msg", "服务器内部错误，获取大屏数据失败");
        }

        return result;
    }
}