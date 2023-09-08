package com.lmluat.league.rest;

import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.service.UserService;
import com.lmluat.league.service.model.UserDTO;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(UserDTO userDTO) throws InputValidationException {
        UserDTO createdUser = userService.create(userDTO);
        return Response.ok(createdUser).build();
    }
}
