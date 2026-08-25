package com.java.talent.batch12.atm.response;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class ResponseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

    private ResponseUtils() {
    }

    public static ResponseEntity<CommonResponse> createCommonErrorResponse(HttpStatus status, String apiName,
                                                                           String apiId, String overview, String message) {
        LOGGER.info("Error Message :{}  : Server Response  is : {} ", message, status.value());

        return new ResponseEntity<>(
                CommonResponse.of(status.value(), apiName, apiId, overview, message,
                        java.time.LocalDateTime.now()),
                status);
    }

    public static ResponseEntity<CommonResponse> createCommonResponse(HttpStatus status, String apiName,
                                                                      String apiId, String overview,
                                                                      String message,
                                                                      Object data) {
        return new ResponseEntity<>(
                CommonResponse.of(status.value(), apiName, apiId, overview, message,
                        java.time.LocalDateTime.now(), data),
                status);
    }

    public static String[] getNullAndExcludedProperties(Object source, String... exclude) {
        var wrapper = new BeanWrapperImpl(source);
        var nullProps = Arrays.stream(wrapper.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(name -> wrapper.getPropertyValue(name) == null)
                .collect(Collectors.toSet());

        if (exclude != null) {
            nullProps.addAll(Arrays.asList(exclude));
        }

        return nullProps.toArray(new String[0]);
    }
}
