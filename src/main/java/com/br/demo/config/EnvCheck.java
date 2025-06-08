package com.br.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvCheck {

    @Value("${SPRING_DATASOURCE_URL}")
    private String datasourceUrl;

    @Value("${SPRING_DATASOURCE_USERNAME}")
    private String datasourceUsername;

    @PostConstruct
    public void checkEnv() {
        System.out.println("✅ SPRING_DATASOURCE_URL: " + datasourceUrl);
        System.out.println("✅ SPRING_DATASOURCE_USERNAME: " + datasourceUsername);
    }
}