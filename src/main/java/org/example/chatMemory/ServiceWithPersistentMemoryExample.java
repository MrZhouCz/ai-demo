package org.example.chatMemory;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;
import static org.mapdb.Serializer.STRING;

public class ServiceWithPersistentMemoryExample {

    interface Assistant {
        String chat(String message);
    }

    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .apiKey("ce250581-d58a-4580-999e-c003385ae61d")
                .modelName("deepseek-v3-2-251201")
                .build();

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryStore(new PersistentChatMemoryStore())
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();

        System.out.println(assistant.chat("Hello, my name is Jonathan."));

        // 注释上一行，并取消注释下一行，查看对话结果
//        System.out.println(assistant.chat("What is my name?"));
    }

    /**
     * 你可以创建自己的 ChatMemoryStore 实现，并在需要时随时存储对话记忆。
     */
    static class PersistentChatMemoryStore implements ChatMemoryStore {

        private final DB db = DBMaker.fileDB("chat-memory.db").closeOnJvmShutdown().make();
        private final Map<String, String> map = db.hashMap("message", STRING, STRING).createOrOpen();

        @Override
        public List<ChatMessage> getMessages(Object memoryId) {
            String json = map.get((String) memoryId);
            return messagesFromJson(json);
        }

        @Override
        public void updateMessages(Object memoryId, List<ChatMessage> messages) {
            map.put((String) memoryId, messagesToJson(messages));
            db.commit();
        }

        @Override
        public void deleteMessages(Object memoryId) {
            map.remove((String) memoryId);
            db.commit();
        }
    }
}
