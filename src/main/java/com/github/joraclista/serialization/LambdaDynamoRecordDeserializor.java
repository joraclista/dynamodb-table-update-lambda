package com.github.joraclista.serialization;

import com.amazonaws.services.dynamodbv2.document.internal.InternalUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alisa
 * version 1.0.
 */
public class LambdaDynamoRecordDeserializor {

    private static class M extends HashMap<String, AttributeValue> {}

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public <T> T getTypeFromAttrJson(String attrJson, Class<T> clazz) throws IOException {
        Map<String, Object> map = InternalUtils.toSimpleMapValue(objectMapper
                .reader()
                .forType(M.class)
                .readValue(attrJson));

        String json = objectMapper.writeValueAsString(map);

        return objectMapper.reader()
                .forType(clazz)
                .readValue(json);
    }

    public <T> T getTypeFromAttrMap(Map<String, AttributeValue> attributeValueMap, Class<T> clazz) throws IOException {
        Map<String, Object> map = InternalUtils.toSimpleMapValue(attributeValueMap);

        String json = objectMapper.writeValueAsString(map);

        return objectMapper.reader()
                .forType(clazz)
                .readValue(json);
    }

    public Map<String, Object> getSimpleMapFromAttrMap(Map<String, AttributeValue> attributeValueMap) {
        return InternalUtils.toSimpleMapValue(attributeValueMap);
    }

    public <T> T getTypeFromAttrFile(File file, Class<T> clazz) throws IOException {
        Map<String, Object> map = InternalUtils.toSimpleMapValue(objectMapper
                .reader()
                .forType(M.class)
                .readValue(file));

        String json = objectMapper.writeValueAsString(map);

        return objectMapper.reader()
                .forType(clazz)
                .readValue(json);
    }

    public <T> T getTypeFromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.reader()
                .forType(clazz)
                .readValue(json);
    }

}
