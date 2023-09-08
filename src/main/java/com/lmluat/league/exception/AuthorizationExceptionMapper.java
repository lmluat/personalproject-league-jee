package com.lmluat.league.exception;

import com.lmluat.league.utils.ApplicationLogger;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AuthorizationExceptionMapper implements ExceptionMapper<AuthorizationException> {
    @Inject
    private ApplicationLogger logger;

    @Override
    public Response toResponse(AuthorizationException e) {
        StackTraceElement[] stackTraceArray = e.getStackTrace();
        String logMessage = String.format("%s:%d - %s",
                stackTraceArray[0].getClassName(),
                stackTraceArray[0].getLineNumber(),
                e.getResponseBody().getErrorMessage());
        logger.logInfo(logMessage);
        ResponseBody responseBody = e.getResponseBody();
        return Response.status(responseBody.getStatus())
                .entity(responseBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
