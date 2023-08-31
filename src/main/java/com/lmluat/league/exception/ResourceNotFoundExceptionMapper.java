package com.lmluat.league.exception;

import com.lmluat.league.rest.TournamentResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    private static final Logger logger = LogManager.getLogger(TournamentResource.class);
    @Override
    public Response toResponse(ResourceNotFoundException e) {
        logger.error(e.getMessage());
        ResponseBody responseBody = e.getResponseBody();
        return Response.status(Response.Status.NOT_FOUND)
                .entity(responseBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
