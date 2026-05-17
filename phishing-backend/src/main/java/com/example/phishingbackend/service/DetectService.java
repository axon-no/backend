package com.example.phishingbackend.service;

import com.example.phishingbackend.entity.DetectTask;
import com.example.phishingbackend.mapper.DetectTaskMapper;
import com.example.phishingbackend.mapper.BlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DetectService {

    @Autowired
    private DetectTaskMapper taskMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BlacklistMapper blacklistMapper;

    public DetectTask processDetection(Long userId, Integer type, String content) {
        DetectTask task = new DetectTask();
        task.setUserId(userId);
        task.setTaskType(type);
        task.setTargetContent(content);
        task.setTaskStatus(1);
        taskMapper.insertTask(task);

        try {
            boolean hitBlacklist = false;
            List<String> dynamicBlacklist = blacklistMapper.getAllUrls();
            for (String badUrl : dynamicBlacklist) {
                if (content.contains(badUrl)) {
                    hitBlacklist = true;
                    break;
                }
            }

            Map<String, Object> pyReq = new HashMap<>();
            pyReq.put("content", content);
            Map<String, Object> pyRes = restTemplate.postForObject("http://127.0.0.1:8000/analyze", pyReq, Map.class);

            System.out.println("🤖 Python 原始返回: " + pyRes);

            if (pyRes != null && pyRes.containsKey("bert_score")) {
                double bScore = Double.parseDouble(pyRes.get("bert_score").toString());
                double uScore = hitBlacklist ? 100.0 : 0.0;

                double fScore;
                if (hitBlacklist) {
                    fScore = 100.0;
                } else {
                    fScore = bScore * 0.6 + uScore * 0.4;
                }

                // 只存分数，不存敏感词
                task.setBertScore(bScore);
                task.setUrlScore(uScore);
                task.setFinalScore(fScore);

                if (fScore > 75) {
                    task.setRiskLevel("DANGER");
                } else if (fScore > 40) {
                    task.setRiskLevel("SUSPICIOUS");
                } else {
                    task.setRiskLevel("SAFE");
                }

                task.setTaskStatus(2);

                System.out.println("✅ 准备写入数据库! 分数: " + fScore);
                taskMapper.updateTaskResult(task);
            }
        } catch (Exception e) {
            System.err.println("调用 Python 接口失败: " + e.getMessage());
            e.printStackTrace();
            task.setTaskStatus(3);
            taskMapper.updateTaskResult(task);
        }
        return task;
    }
}