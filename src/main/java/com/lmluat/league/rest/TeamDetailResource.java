package com.lmluat.league.rest;

import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.service.TeamDetailService;
import com.lmluat.league.service.model.TeamDetail;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;

@Path("/team-details")
public class TeamDetailResource {
    @Inject
    private TeamDetailService teamDetailService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok(teamDetailService.getAll()).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(TeamDetail teamDetail) throws InputValidationException {
        TeamDetail createdTeamDetail = teamDetailService.create(teamDetail);
        return Response.created(URI.create("team-details/" + createdTeamDetail.getId())).entity(createdTeamDetail).build();
    }

    @GET
    @Path("/criteria")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByCriteria(@QueryParam("teamName") String teamName, @QueryParam("tournamentName") String tournamentName, @QueryParam("coachName") String coachName) {
        return Response.ok(teamDetailService.getByCriteria(Optional.ofNullable(teamName), Optional.ofNullable(tournamentName), Optional.ofNullable(coachName))).build();
    }


}
