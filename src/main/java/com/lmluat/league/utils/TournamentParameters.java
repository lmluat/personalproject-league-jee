package com.lmluat.league.utils;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

@Getter
@Setter
public class TournamentParameters {
    @FormParam("name")
    private String name;

    @FormParam("startDate")
    private LocalDate startDate;

    @FormParam("endDate")
    private LocalDate endDate;

    @FormParam("season")
    private String season;

    @FormParam("sponsor")
    private String sponsor;
}

