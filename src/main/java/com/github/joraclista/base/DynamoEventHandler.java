package com.github.joraclista.base;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface DynamoEventHandler<T> {
    /**
     * designed to handle event coming from DynamoDB stream
     * @param newImage - image of dynamoDB record after event occurred
     * @param oldImage - image of dynamoDB record before event occurred
     * @throws Exception
     */
    void handle(T newImage, T oldImage) throws Exception;

}
