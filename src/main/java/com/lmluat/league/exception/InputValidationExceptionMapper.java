package com.lmluat.league.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InputValidationExceptionMapper implements ExceptionMapper<InputValidationException> {
    @Override
    public Response toResponse(InputValidationException e) {
        ResponseBody responseBody = e.getResponseBody();
        return Response.status(responseBody.getStatus())
                .entity(responseBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
