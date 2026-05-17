package com.example.phishingbackend.mapper;

import com.example.phishingbackend.entity.DetectTask;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options; // 👈 新增这一行的导入
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface DetectTaskMapper {

    @Insert("INSERT INTO detect_task (user_id, task_type, target_content, task_status) " +
            "VALUES (#{userId}, #{taskType}, #{targetContent}, #{taskStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id") // 👈 终极救星：告诉 MyBatis 把生成的 ID 赋值给 task 实体类！
    int insertTask(DetectTask task);

    @Update("UPDATE detect_task SET bert_score=#{bertScore}, url_score=#{urlScore}, " +
            "final_score=#{finalScore}, risk_level=#{riskLevel}, task_status=#{taskStatus} " +
            "WHERE id=#{id}")
    int updateTaskResult(DetectTask task);

    @Select("SELECT id, user_id as userId, task_type as taskType, " +
            "target_content as targetContent, bert_score as bertScore, " +
            "url_score as urlScore, final_score as finalScore, " +
            "risk_level as riskLevel, task_status as taskStatus, " +
            "create_time as createTime " +
            "FROM detect_task WHERE user_id = #{userId} ORDER BY id DESC LIMIT 20")
    List<DetectTask> selectByUserId(Long userId);
}