package com.example.phishingbackend.controller;

// 👇 主要是这三行，把 aicomponents 改成了 aigc
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;

import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.InputRequiredException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiChatController {

    // 🚨 务必替换为你自己的 API KEY
    private final String apiKey = "sk-28e2361c88874daf883f48fa569bfe84";

    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> params) {
        String userQuestion = params.get("message");

        try {
            Generation gen = new Generation();

            // 设定 AI 的“人设”：校园反诈专家
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("你是一个专业的校园网络安全和反诈骗专家。你的名字叫‘校安小助手’。" +
                            "请针对学生咨询的疑似诈骗话术、短信或情况提供专业、亲切的建议。")
                    .build();

            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(userQuestion)
                    .build();

            GenerationParam param = GenerationParam.builder()
                    .model("qwen-plus") // 使用 Qwen-Plus 模型，性价比最高
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .topP(0.8)
                    .apiKey(apiKey)
                    .build();

            GenerationResult result = gen.call(param);
            // 提取 AI 的回答内容
            return result.getOutput().getChoices().get(0).getMessage().getContent();

        } catch (NoApiKeyException | InputRequiredException e) {
            return "AI 服务配置异常，请检查 API Key";
        } catch (Exception e) {
            return "小助手暂时开小差了，请稍后再试: " + e.getMessage();
        }
    }
}