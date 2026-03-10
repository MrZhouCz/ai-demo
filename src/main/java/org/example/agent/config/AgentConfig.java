package org.example.agent.config;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.example.agent.service.AgentService;
import org.example.agent.tool.JavaTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String apiKey;

    @Value("${langchain4j.open-ai.chat-model.model-name}")
    private String modelName;

    @Value("${langchain4j.open-ai.chat-model.base-url}")
    private String baseUrl;

    @Value("${langchain4j.open-ai.chat-model.temperature:0.7}")
    private Double temperature;

    @Bean
    public ChatLanguageModel model() {
        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(temperature)
                .build();
    }

    @Bean
    public AgentService agentService(ChatLanguageModel model, JavaTools javaTools) {
        return AiServices.builder(AgentService.class)
                .chatLanguageModel(model)
                .tools(javaTools) // 工具注入
                .chatMemoryProvider(userId ->
                        MessageWindowChatMemory.withMaxMessages(10) // 记忆10轮
                )
                .build();
    }
}
