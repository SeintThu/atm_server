package com.java.talent.batch12.atm.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommonResponse {

    private Integer httpStatusCode;
    private String apiName;
    private String apiId;
    private String overview;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private Object data;

    protected CommonResponse() {
    }

    protected CommonResponse(Integer httpStatusCode, String apiName, String apiId, String overview, String message,
                             LocalDateTime systemDateTime) {
        this(httpStatusCode, apiName, apiId, overview, message, systemDateTime, LocalDateTime.now(), null);
    }

    protected CommonResponse(Integer httpStatusCode, String apiName, String apiId, String overview, String message,
                             LocalDateTime systemDateTime, Object data) {
        this(httpStatusCode, apiName, apiId, overview, message, systemDateTime, LocalDateTime.now(), data);
    }

    private CommonResponse(Integer httpStatusCode, String apiName, String apiId, String overview, String message,
                           LocalDateTime systemDateTime, LocalDateTime timestamp, Object data) {
        this.httpStatusCode = httpStatusCode;
        this.apiName = apiName;
        this.apiId = apiId;
        this.overview = overview;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
    }

    public static CommonResponse of(Integer httpStatusCode, String apiName, String apiId, String overview,
                                    String message, LocalDateTime systemDateTime) {
        return new CommonResponse(httpStatusCode, apiName, apiId, overview, message, systemDateTime,
                LocalDateTime.now(), null);
    }

    public static CommonResponse of(Integer httpStatusCode, String apiName, String apiId, String overview,
                                    String message, LocalDateTime systemDateTime, Object data) {
        return new CommonResponse(httpStatusCode, apiName, apiId, overview, message, systemDateTime,
                LocalDateTime.now(), data);
    }

}
