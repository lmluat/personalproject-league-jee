package com.lmluat.league.rest;

import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.InputValidationExceptionMapper;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.TournamentService;
import com.lmluat.league.service.model.Tournament;
import com.lmluat.league.utils.TournamentParameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;


@Path("/tournaments")
public class TournamentResource {
    private static final Logger logger = LogManager.getLogger(TournamentResource.class);
    @Inject
    private TournamentService tournamentService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        logger.info("Getting all tournaments");
        return Response.ok(tournamentService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws ResourceNotFoundException {
        return Response.ok(tournamentService.getById(id)).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Tournament tournament) throws InputValidationException {
            Tournament createdTournament = tournamentService.create(tournament);
            return Response.created(URI.create("tournament/" + createdTournament.getId())).entity(createdTournament).status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/form")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createWithForm(@FormParam("name") String name,
                                   @FormParam("startDate") LocalDate startDate,
                                   @FormParam("endDate") LocalDate endDate,
                                   @FormParam("season") String season,
                                   @FormParam("sponsor") String sponsor) throws InputValidationException {

        Tournament tournament = Tournament.builder()
                .tournamentName(name)
                .startDate(startDate)
                .endDate(endDate)
                .season(season)
                .sponsor(sponsor)
                .build();

        System.out.println(season);

        Tournament createdTournament = tournamentService.create(tournament);

        return Response.created(URI.create("tournament/" + createdTournament.getId())).entity(createdTournament).status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/form-bean-param")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createWithForm(@BeanParam TournamentParameters tournamentParameters) throws InputValidationException {

        Tournament tournament = Tournament.builder()
                .tournamentName(tournamentParameters.getName())
                .startDate(tournamentParameters.getStartDate())
                .endDate(tournamentParameters.getEndDate())
                .season(tournamentParameters.getSeason())
                .sponsor(tournamentParameters.getSponsor())
                .build();

        Tournament createdTournament = tournamentService.create(tournament);

        return Response.created(URI.create("tournament/" + createdTournament.getId())).entity(createdTournament).status(Response.Status.CREATED).build();
    }

}
