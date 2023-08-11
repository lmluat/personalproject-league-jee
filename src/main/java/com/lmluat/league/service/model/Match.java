package com.lmluat.league.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private Long id;
    @NotNull
    private LocalDate date;

    private String location;

    @NotNull
    private Long casterId;

    @NotNull
    private Long tournamentId;
}
