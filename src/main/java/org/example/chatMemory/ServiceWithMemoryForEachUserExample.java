package org.example.chatMemory;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public class ServiceWithMemoryForEachUserExample {

    interface Assistant {

        String chat(@MemoryId int id, @UserMessage String message);
    }

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .apiKey("ce250581-d58a-4580-999e-c003385ae61d")
                .modelName("deepseek-v3-2-251201")
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();

        System.out.println(assistant.chat(1, "Hello, my name is tom."));
        System.out.println(assistant.chat(2, "Hello, my name is cherry."));
        System.out.println(assistant.chat(1, "What is my name?"));
        System.out.println(assistant.chat(2, "What is my name?"));
    }
}
