package com.lmluat.league.exception;

import lombok.Getter;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
public class InputValidationException extends Exception {

    @Getter
    private final transient ResponseBody responseBody;

    public InputValidationException(String errorMessage, String errorKey) {
        this.responseBody = new ResponseBody(errorMessage, errorKey, Response.Status.BAD_REQUEST);
    }
}
