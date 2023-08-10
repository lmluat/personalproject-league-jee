package com.lmluat.league.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeamDetail {

    private Long id;

    @NotBlank
    private Long tournamentId;

    @NotBlank
    private Long teamId;

    @NotBlank
    private Long coachId;

    private String tournamentName;

    private String teamName;

    private String coachName;

    @Size(min = 1, message = "Sponsor is required")
    private String sponsor;
}
