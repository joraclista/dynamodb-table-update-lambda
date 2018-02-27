package com.github.joraclista;

import com.amazonaws.services.dynamodbv2.model.OperationType;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Alisa
 * version 1.0.
 */
@RunWith(JUnitPlatform.class)
public class DynamoDbUpdatesHandlerTest {

    interface MixIn {
        void setEventName(String eventName);
        @JsonIgnore
        void setEventName(OperationType eventName);
        @JsonIgnore
        void setStreamViewType(StreamViewType streamViewType);
    }

    private <T> T read(String fileName, Class<T> type) throws IOException {
        return new ObjectMapper()
                .addMixIn(Record.class, MixIn.class)
                .addMixIn(StreamRecord.class, MixIn.class)
                .readValue(Paths.get("src","test", "resources", fileName).toFile(), type);

    }

    @Test
    void merchantRecordModifyTest() throws IOException {
        DynamodbEvent input = read("dynamodb-merchant-modify.json", DynamodbEvent.class);
        input.getRecords().forEach(r -> r.setEventName(OperationType.MODIFY));
        input.getRecords().forEach(r -> assertTrue(r.getEventSourceARN().contains("table/Merchants")));
        new ElasticReindexingLambda().handleRequest(input, null);
    }

    @Test
    void productRecordModifyTest() throws IOException {
        DynamodbEvent input = read("dynamodb-product-modify.json", DynamodbEvent.class);
        input.getRecords().forEach(r -> r.setEventName(OperationType.MODIFY));
        input.getRecords().forEach(r -> assertTrue(r.getEventSourceARN().contains("table/Products")));
        new ElasticReindexingLambda().handleRequest(input, null);
    }

    @Test
    void productRecordRemoveTest() throws IOException {
        DynamodbEvent input = read("dynamodb-product-remove.json", DynamodbEvent.class);
        input.getRecords().forEach(r -> r.setEventName(OperationType.REMOVE));
        input.getRecords().forEach(r -> assertTrue(r.getEventSourceARN().contains("table/Products")));
        new ElasticReindexingLambda().handleRequest(input, null);
    }

    @Test
    void productRecordInsertTest() throws IOException {
        DynamodbEvent input = read("dynamodb-product-insert.json", DynamodbEvent.class);
        input.getRecords().forEach(r -> r.setEventName(OperationType.INSERT));
        input.getRecords().forEach(r -> assertTrue(r.getEventSourceARN().contains("table/Products")));
        new ElasticReindexingLambda().handleRequest(input, null);
    }
}
