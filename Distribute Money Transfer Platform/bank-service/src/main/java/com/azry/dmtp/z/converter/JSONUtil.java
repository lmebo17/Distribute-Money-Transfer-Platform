package com.azry.dmtp.z.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JSONUtil {

    private final ObjectMapper objectMapper;

    public String serialize(Object[] args) {
        try {
            return objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object array to JSON", e);
        }
    }

    public Object[] deserialize(String jsonString, Class<?>[] types) {
        try {
            return objectMapper.readValue(jsonString, Object[].class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to object array", e);
        }
    }
}
