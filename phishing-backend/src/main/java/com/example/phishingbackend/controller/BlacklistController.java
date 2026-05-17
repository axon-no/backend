package com.example.phishingbackend.controller;

import com.example.phishingbackend.mapper.BlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/blacklist")
@CrossOrigin(origins = "*")
public class BlacklistController {

    @Autowired
    private BlacklistMapper blacklistMapper;

    @GetMapping("/list")
    public List<Map<String, Object>> getList() {
        return blacklistMapper.getAllBlacklist();
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, String> params) {
        blacklistMapper.addBlacklist(params.get("url"), params.get("remark"));
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "添加成功");
        return res;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        blacklistMapper.deleteById(id);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "删除成功");
        return res;
    }
}