package com.lmluat.league.exception;

import com.lmluat.league.rest.TournamentResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final Logger logger = LogManager.getLogger(TournamentResource.class);
    @Override
    public Response toResponse(ConstraintViolationException e) {

        logger.error(e.getMessage());

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
