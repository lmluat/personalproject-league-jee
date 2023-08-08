package com.lmluat.league.service.model;

import com.lmluat.league.entity.PlayerEntity;
import com.lmluat.league.entity.TeamDetailEntity;
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
public class PlayerDetail {
    private Long id;

    private PlayerEntity playerEntity;

    private TeamDetailEntity teamDetailEntity;

    private String position;

    private Boolean isCaptain;
}
