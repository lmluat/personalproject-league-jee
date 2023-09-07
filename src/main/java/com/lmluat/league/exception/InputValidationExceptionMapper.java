package com.lmluat.league.exception;

import com.lmluat.league.utils.ApplicationLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InputValidationExceptionMapper implements ExceptionMapper<InputValidationException> {

    @Inject
    private ApplicationLogger logger;

    @Override
    public Response toResponse(InputValidationException e) {
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
