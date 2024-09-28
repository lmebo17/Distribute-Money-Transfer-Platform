package com.azry.dmtp.transferrepository.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class OpenApiConfig {
    @Value("${openapi.servers:}")
    private List<String> serverUrls;

    @Bean
    public OpenAPI customOpenApi() {
        List<Server> serverList = serverUrls
                .stream()
                .map(url -> {
                    Server server = new Server();
                    server.setUrl(url);
                    return server;
                })
                .collect(Collectors.toList());
        return new OpenAPI().servers(serverList);
    }
}