package com.example.phishingbackend.entity;

public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String nickname;

    // 👇 这就是刚才报错找不到的新字段：账号状态
    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    // 👇 补上对应的 get 和 set 方法
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}