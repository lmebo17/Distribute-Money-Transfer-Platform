package com.azry.dmtp.z.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Converter
@RequiredArgsConstructor
public class MethodArgumentsConverter implements AttributeConverter<Object[], String> {

    private final JSONUtil jsonUtil;

    @Override
    public String convertToDatabaseColumn(Object[] objects) {
        return jsonUtil.serialize(objects);
    }

    @Override
    public Object[] convertToEntityAttribute(String s) {
        return jsonUtil.deserialize(s, null);
    }
}
