package com.hackday.knowUrHub.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by abhishek.ar on 6/5/15.
 */

public enum ObjectMapperProvider {

    INSTANCE;
    private ObjectMapper objectMapper;

    public void set(ObjectMapper objectMapper) {
        if (objectMapper != null) {
            this.objectMapper = objectMapper;
        }
    }

    public ObjectMapper get() {
        return objectMapper;
    }

}

