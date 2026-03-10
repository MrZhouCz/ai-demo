package org.example.agent.Controller;

import org.example.agent.service.AgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/agent/chat")
    public String chat(@RequestParam String userId, @RequestParam String msg) {
        return agentService.chat(userId, msg);
    }
}