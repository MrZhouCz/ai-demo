package org.example.day2;

import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class demo1 {

    public static void main(String[] args) {

        /*
          deepseek-v3-2只支持文本输入
         */
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .apiKey("ce250581-d58a-4580-999e-c003385ae61d")
                .modelName("deepseek-v3-2-251201")
                .build();

        ChatResponse chatResponse = model.chat(
                UserMessage.from("你好，你叫什么？"),
                UserMessage.from("我叫Jonathan，记住我的名字")
        );

        System.out.println(chatResponse.aiMessage().text());
    }
}
