package com.example.langchain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = LocalAiProperties.prefix)
public class LocalAiProperties {

    static final String prefix="langchain4j.local-ai";

    @NestedConfigurationProperty
    ChatModelProperties chatModel;
}
