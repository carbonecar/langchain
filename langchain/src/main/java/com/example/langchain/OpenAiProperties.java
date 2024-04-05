package com.example.langchain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAiProperties {

    private String openApiKey;

    private String modelName;

    private Double getTemperature;
}
