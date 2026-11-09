package com.arda.tasktracking.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(int status,
                       String message,
                       Map<String, String> fieldErrors,
                       Instant timestamp) {


        public ApiError(int status, String message){
            this(status, message, null, Instant.now());
        }

}
