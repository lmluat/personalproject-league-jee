package com.lmluat.league.service.model.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDetailDTO {
    private Long tournamentId;
    private Long teamId;
    private Long coachId;
    private String sponsor;
}
