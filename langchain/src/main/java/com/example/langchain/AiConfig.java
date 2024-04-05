package com.example.langchain;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class AiConfig {

    @Bean
    ApplicationRunner interactiveChatRunner(WorkaholicsAgent agent) {
        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("User: ");
                String userMessage = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userMessage)) {
                    break;
                }

                String agentMessage = agent.chat(userMessage);
                System.out.println("Agent: " + agentMessage);
            }

            scanner.close();
        };
    }

    @Bean
    public WorkaholicsAgent customerSupportAgent(ChatLanguageModel chatLanguageModel) {
        return AiServices.builder(WorkaholicsAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .build();
    }

    @Bean
    public ChatLanguageModel openAiChatModel(OpenAiProperties openAiProperties) {
        ChatLanguageModel chatLanguageModel = OpenAiChatModel.builder()
                .apiKey(openAiProperties.openApiKey())
                .modelName(openAiProperties.getModelName())
                .temperature(openAiProperties.getTemperature())
                .logRequests(true)
                .logResponses(true)
                .build();
        return chatLanguageModel;
    }

}
