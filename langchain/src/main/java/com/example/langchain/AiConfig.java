package com.example.langchain;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

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
    public WorkaholicsAgent customerSupportAgent(ChatLanguageModel chatLanguageModel,
                                                 ContentRetriever contentRetriever) {
        return AiServices.builder(WorkaholicsAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .contentRetriever(contentRetriever)
                .build();
    }

    @Bean
    public ChatLanguageModel openAiChatModel(OpenAiProperties openAiProperties) {
        return OpenAiChatModel.builder()
                .apiKey(openAiProperties.getApiKey())
                .modelName(openAiProperties.getModelName())
                .temperature(openAiProperties.getTemperature())
                .logRequests(true)
                .logResponses(true)
                .build();

    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    EmbeddingStore embeddingStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader) {
        return new InMemoryEmbeddingStore();
    }

    @Bean
    ContentRetriever contentRetriever(EmbeddingStore embeddingStore, EmbeddingModel embeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(1)
                .minScore(0.6)
                .build();
    }
}
