package com.lmluat.league.rest;

import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.TeamService;
import com.lmluat.league.service.model.Team;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/teams")
public class TeamResource {

    @Inject
    private TeamService teamService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() throws ResourceNotFoundException {
        return Response.ok(teamService.getAll()).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Team team) throws InputValidationException {
        Team createdTeam = teamService.create(team);
        return Response.created(URI.create("skills/" + createdTeam.getId())).entity(createdTeam).build();
    }

}
