package com.example.phishingbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    // 1. 统计核心指标：总数、拦截数、活跃用户、平均分
    @Select("SELECT " +
            "COUNT(*) as totalChecks, " +
            "SUM(CASE WHEN final_score > 75 THEN 1 ELSE 0 END) as intercepted, " +
            "COUNT(DISTINCT user_id) as activeUsers " +
            "FROM detect_task")
    Map<String, Object> getCoreStats();

    // 2. 统计风险分布（饼图）
    @Select("SELECT risk_level as name, COUNT(*) as value FROM detect_task GROUP BY risk_level")
    List<Map<String, Object>> getRiskDistribution();

    // 3. 统计近7天检测趋势（折线图）
    @Select("SELECT DATE_FORMAT(create_time, '%m-%d') as date, COUNT(*) as count " +
            "FROM detect_task " +
            "WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY date " +
            "ORDER BY date ASC")
    List<Map<String, Object>> getDailyTrend();
}