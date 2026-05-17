package com.example.phishingbackend.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface BlacklistMapper {
    // 1. 获取所有黑名单（供后台表格展示）
    @Select("SELECT * FROM url_blacklist ORDER BY create_time DESC")
    List<Map<String, Object>> getAllBlacklist();

    // 2. 仅获取所有 URL 字符串（供 AI 审核服务快速比对）
    @Select("SELECT url FROM url_blacklist")
    List<String> getAllUrls();

    // 3. 添加新黑名单
    @Insert("INSERT INTO url_blacklist(url, remark) VALUES(#{url}, #{remark})")
    void addBlacklist(@Param("url") String url, @Param("remark") String remark);

    // 4. 删除黑名单
    @Delete("DELETE FROM url_blacklist WHERE id = #{id}")
    void deleteById(@Param("id") Long id);
}