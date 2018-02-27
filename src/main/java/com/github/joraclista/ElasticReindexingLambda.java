package com.github.joraclista;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.github.joraclista.base.DynamoEventTypes;
import com.github.joraclista.handlers.ModifyRecordHandler;
import com.github.joraclista.handlers.RemoveRecordHandler;
import com.github.joraclista.serialization.LambdaDynamoRecordDeserializor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
public class ElasticReindexingLambda implements RequestHandler<DynamodbEvent, Object> {

    private static final String STATUS_OK = "OK";
    private static final String STATUS_FAILED = "Failed";

    @Override
    public Object handleRequest(DynamodbEvent input, Context context) {
       Environment environment = new Environment(Regions.US_EAST_1);
        // source.arn = arn:aws:dynamodb:us-east-1:324609709848:table/Products/stream/2017-02-15T10:26:02.608
        log.info("handleRequest environment = {}; input.size = {} ", environment, input.getRecords().size());
        try {
            for (DynamodbEvent.DynamodbStreamRecord r : input.getRecords()) {
                log.info("Event id = {}; name = {}; source = {}; source.arn = {};",
                        r.getEventID(), r.getEventName(), r.getEventSource(), r.getEventSourceARN());

                String tableName = r.getEventSourceARN().split("/")[1];


                log.info("tableName = [{}]; ", tableName);

                DynamoEventTypes dynamoEventType = DynamoEventTypes.valueOf(r.getEventName());

                StreamRecord sr = r.getDynamodb();


                switch (dynamoEventType) {
                    case MODIFY:
                        new ModifyRecordHandler(environment).handle(getFromMap(sr.getNewImage()), getFromMap(sr.getOldImage()));
                        break;
                    case INSERT:
                        new ModifyRecordHandler(environment).handle(getFromMap(sr.getNewImage()), null);
                        break;
                    case REMOVE:
                        new RemoveRecordHandler(environment).handle(null, getFromMap(sr.getOldImage()));
                        break;
                    default:
                        log.warn("This couldn't be true, right?");
                }

            }
        } catch (Exception e) {
            log.error("handleRequest: couldn't handle request due to: error.msg = {} ", e.getMessage());
            return STATUS_FAILED;
        }

        return STATUS_OK;
    }

    private Map<String, Object> getFromMap(Map<String, AttributeValue> map) throws java.io.IOException {
        return new LambdaDynamoRecordDeserializor().getSimpleMapFromAttrMap(map);
    }
}
