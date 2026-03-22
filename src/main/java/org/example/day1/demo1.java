package org.example.day1;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class demo1 {

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .apiKey("ce250581-d58a-4580-999e-c003385ae61d")
                .modelName("deepseek-v3-2-251201")
                .build();

        String answer = model.chat("我叫什么");
        System.out.println(answer);
    }
}
