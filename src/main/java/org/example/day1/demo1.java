package org.example.day1;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static dev.langchain4j.model.LambdaStreamingResponseHandler.onPartialResponse;

public class demo1 {

    public static void main(String[] args) {

//        OpenAiChatModel model = OpenAiChatModel.builder()
//                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
//                .apiKey("ce250581-d58a-4580-999e-c003385ae61d")
//                .modelName("deepseek-v3-2-251201")
//                .build();
//
//        String answer = model.chat("我叫什么");
//        System.out.println(answer);

        StreamingChatModel streamingModel = OpenAiStreamingChatModel.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .apiKey("ce250581-d58a-4580-999e-c003385ae61d")
                .modelName("deepseek-v3-2-251201")
                .build();

        // 使用 CompletableFuture 来等待异步完成
        CompletableFuture<Void> future = new CompletableFuture<>();

        streamingModel.chat("给我讲个笑话", new StreamingChatResponseHandler() {

            @Override
            public void onPartialResponse(String partialResponse) {
                System.out.print(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                future.complete(null); // 标记完成
            }

            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
                future.completeExceptionally(error); // 标记异常
            }
        });

        // 阻塞等待完成
        try {
            future.join();
        } catch (Exception e) {
            System.err.println("请求失败：" + e.getMessage());
        }
    }
}
