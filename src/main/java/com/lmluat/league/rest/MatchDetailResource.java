package com.lmluat.league.rest;

import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.MatchDetailService;
import com.lmluat.league.service.model.MatchDetail;

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
import java.time.LocalDate;
import java.util.Optional;

@Path("/match-details")
public class MatchDetailResource {
    @Inject
    private MatchDetailService matchDetailService;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(MatchDetail matchDetail) throws InputValidationException, ResourceNotFoundException {
        MatchDetail createdMatchDetail = matchDetailService.create(matchDetail);
        return Response.created(URI.create("team-details/" + createdMatchDetail.getId())).entity(createdMatchDetail).build();
    }

    @POST
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(MatchDetail matchDetail, @PathParam("id") Long id) throws InputValidationException, ResourceNotFoundException {
        MatchDetail updatedMatchDetail = matchDetailService.update(matchDetail, id);
        return Response.created(URI.create("team-details/" + updatedMatchDetail.getId())).entity(updatedMatchDetail).build();
    }

    @GET
    @Path("/ranking")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByCriteria(@QueryParam("tournamentId") Long tournamentId) {
        return Response.ok(matchDetailService.getRankingTableByTournamentId(tournamentId)).build();
    }

    @GET
    @Path("/dates")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBetweenDates(@QueryParam("tournamentId") Optional<Long> tournamentId, @QueryParam("startDate") LocalDate startDate, @QueryParam("endDate") LocalDate endDate) throws ResourceNotFoundException {
        return Response.ok(matchDetailService.getBetweenDates(tournamentId, startDate, endDate)).build();
    }
}
