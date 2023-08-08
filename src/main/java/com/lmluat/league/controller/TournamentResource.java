package com.lmluat.league.controller;

import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.TournamentService;
import com.lmluat.league.service.model.Tournament;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


@Path("/tournaments")
public class TournamentResource {

    @Inject
    private TournamentService tournamentService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() throws ResourceNotFoundException {
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
    public Response create(Tournament tournament) {
        try {
            Tournament createTournament = tournamentService.create(tournament);
            return Response.created(URI.create("skills/" + createTournament.getId())).entity(createTournament).status(Response.Status.CREATED).build();
        } catch (InputValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getResponseBody()).build();
        }
    }

}
