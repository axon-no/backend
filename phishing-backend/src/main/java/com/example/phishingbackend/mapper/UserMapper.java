package com.example.phishingbackend.mapper;

import com.example.phishingbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

// 👇 就是缺了下面这两行，我已经帮你补上了
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    // 1. 根据用户名查询（登录用）
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    // 2. 获取所有用户列表 (后台管理用)
    @Select("SELECT id, username, role, nickname, status, create_time FROM user ORDER BY create_time DESC")
    List<Map<String, Object>> getAllUsers();

    // 3. 更新封禁状态
    @Update("UPDATE user SET status = #{status} WHERE id = #{id}")
    void updateUserStatus(@Param("id") Long id, @Param("status") Integer status);

    // 4. 重置密码
    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    void resetPassword(@Param("id") Long id, @Param("password") String password);
}