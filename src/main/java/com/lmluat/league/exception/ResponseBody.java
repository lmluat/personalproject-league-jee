package com.lmluat.league.exception;

import lombok.Getter;

import javax.ws.rs.core.Response;
@Getter
public class ResponseBody {
    private final String errorMessage;
    private final String errorKey;

    private final Response.Status status;

    public ResponseBody(String errorMessage, String errorKey, Response.Status status) {
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
