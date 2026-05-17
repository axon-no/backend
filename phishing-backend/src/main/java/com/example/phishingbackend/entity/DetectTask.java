package com.example.phishingbackend.entity;

import java.util.Date;

public class DetectTask {
    private Long id;
    private Long userId;
    private Integer taskType;     // 1-文本, 2-URL, 3-多模态
    private String targetContent;
    private Double bertScore;
    private Double urlScore;
    private Double finalScore;
    private String riskLevel;     // SAFE, SUSPICIOUS, DANGER
    private Integer taskStatus;   // 0-处理中, 2-完成, 3-失败
    private Date createTime;
    private Date updateTime;

    // ======== 下面是绝对不会报错的 Get 和 Set 方法 ========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getTaskType() { return taskType; }
    public void setTaskType(Integer taskType) { this.taskType = taskType; }

    public String getTargetContent() { return targetContent; }
    public void setTargetContent(String targetContent) { this.targetContent = targetContent; }

    public Double getBertScore() { return bertScore; }
    public void setBertScore(Double bertScore) { this.bertScore = bertScore; }

    public Double getUrlScore() { return urlScore; }
    public void setUrlScore(Double urlScore) { this.urlScore = urlScore; }

    public Double getFinalScore() { return finalScore; }
    public void setFinalScore(Double finalScore) { this.finalScore = finalScore; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public Integer getTaskStatus() { return taskStatus; }
    public void setTaskStatus(Integer taskStatus) { this.taskStatus = taskStatus; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}