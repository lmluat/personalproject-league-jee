package com.lmluat.league.exception;

import lombok.Getter;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
public class ResourceNotFoundException extends Exception{

    @Getter
    private final transient ResponseBody responseBody;
    public ResourceNotFoundException(String errorMessage, String errorKey){
        this.responseBody = new ResponseBody(errorMessage, errorKey, Response.Status.NOT_FOUND);
    }

}
