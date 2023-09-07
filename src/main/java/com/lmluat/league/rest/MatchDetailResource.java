package com.lmluat.league.rest;

import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.MatchDetailService;
import com.lmluat.league.service.model.MatchDetail;
import com.lmluat.league.utils.ApplicationLogger;

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
    private ApplicationLogger logger;
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
        logger.logInfo("Get ranking table");
        return Response.ok(matchDetailService.getRankingTableByTournamentId(tournamentId)).build();
    }

    @GET
    @Path("/dates")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBetweenDates(@QueryParam("tournamentId") Optional<Long> tournamentId, @QueryParam("startDate") LocalDate startDate, @QueryParam("endDate") LocalDate endDate) throws ResourceNotFoundException {
        return Response.ok(matchDetailService.getBetweenDates(tournamentId, startDate, endDate)).build();
    }
    @GET
    @Path("/tournament-name")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByTeamName(@QueryParam("tournamentName") String tournamentName) {
        logger.logInfo("Get by team name: " + tournamentName);
        return Response.ok(matchDetailService.getByTournamentName(tournamentName)).build();
    }

    @GET
    @Path("/criteria")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByCriteria(@QueryParam("teamId") Optional<Long> teamId, @QueryParam("tournamentId") Optional<Long> tournamentId) {
        logger.logInfo("Get by criteria");
        return Response.ok(matchDetailService.getByCriteria(teamId,tournamentId)).build();
    }

    @GET
    @Path("/winning-team")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByWinningTeam(@QueryParam("teamName") String teamName) {
        logger.logInfo("Get by winning team name: " + teamName);
        return Response.ok(matchDetailService.getByWinningTeam(teamName)).build();
    }

    @GET
    @Path("/time")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByDates(@QueryParam("startDate") LocalDate startDate, @QueryParam("endDate") LocalDate endDate) {
        logger.logInfo("Get by dates");
        return Response.ok(matchDetailService.getByDates(startDate, endDate)).build();
    }

}
