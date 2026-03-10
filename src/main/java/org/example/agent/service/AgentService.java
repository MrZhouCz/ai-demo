package org.example.agent.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface AgentService {

    @SystemMessage("""
            你是Java后端AI助手，专业、简洁、给可运行代码。
            必须先调用工具，再回答用户。
            不知道就说不知道，不要编造。""")
    String chat(@MemoryId String userId, @UserMessage String msg);
}
