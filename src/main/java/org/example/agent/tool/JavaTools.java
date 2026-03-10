package org.example.agent.tool;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JavaTools {

    @Tool("获取当前系统时间")
    public String nowTime() {
        return LocalDateTime.now().toString();
    }

    @Tool("计算a + b")
    public int add(int a, int b) {
        return a + b;
    }
}
