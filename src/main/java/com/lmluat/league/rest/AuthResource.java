package com.lmluat.league.rest;

import com.lmluat.league.exception.AuthorizationException;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.service.AuthService;
import com.lmluat.league.utils.JwtRequest;
import com.lmluat.league.utils.JwtResponse;
import com.lmluat.league.utils.JwtUtils;

import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {
    @Inject
    private AuthService authService;
    @Inject
    private JwtUtils jwtUtils;

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(JwtRequest request) throws InputValidationException, AuthorizationException, AuthenticationException {
        JwtResponse jwtResponse = jwtUtils.generateJwtResponse(request);
        return Response.ok(jwtResponse).build();
    }
}
