package com.azry.dmtp.z.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Builder
@Data
@Configuration
public class ObjectMapperConfig {

    private static final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return builder.build().registerModule(new JavaTimeModule());
    }
}
