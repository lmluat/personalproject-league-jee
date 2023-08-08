package com.lmluat.league.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        StackTraceElement[] stackTraceArray = e.getStackTrace();
        String logMessage = String.format("%s:%d - %s",
                stackTraceArray[0].getClassName(),
                stackTraceArray[0].getLineNumber(),
                e.getMessage());

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<ResponseBody> responses = new ArrayList<>();
        for (ConstraintViolation<?> constraint : violations) {
            String errorMessage = constraint.getMessage();
            Response.Status status = Response.Status.BAD_REQUEST;
            String errorKey = ErrorMessage.errorKeyAndMessageMap().get(errorMessage);
            ResponseBody response = new ResponseBody(errorMessage, errorKey, status);
            responses.add(response);
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(responses)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
