package org.example.day3;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class demo1 {

    interface Assistant {

        String chat(String message);
    }

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .apiKey("ce250581-d58a-4580-999e-c003385ae61d")
                .modelName("deepseek-v3-2-251201")
                .build();

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();

        String answer1 = assistant.chat("2+3=?");
        System.out.println(answer1);
        String answer2 = assistant.chat("10-1=?");
        System.out.println(answer2);
        String answer3 = assistant.chat("第一次计算结果是多少？");
        System.out.println(answer3);
    }
}
