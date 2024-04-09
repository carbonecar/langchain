package com.example.langchain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "open-ai.chat-model")
public class OpenAiProperties {

    private String apiKey;

    private String modelName;

    private Double temperature;

}
