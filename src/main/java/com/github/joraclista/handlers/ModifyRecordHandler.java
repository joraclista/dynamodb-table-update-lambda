package com.github.joraclista.handlers;

import com.github.joraclista.Environment;
import com.github.joraclista.base.DynamoEventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
public class ModifyRecordHandler<T> implements DynamoEventHandler<T> {

    private final Environment environment;

    public ModifyRecordHandler(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void handle(T newImage, T oldImage) {
        log.info("handle: newImage = {}; oldImage = {}; ", newImage, oldImage);
    }

}
