package com.lmluat.league.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static com.lmluat.league.exception.ErrorMessage.INPUT_DATA_NULL_OR_BLANK;
import static com.lmluat.league.exception.ErrorMessage.INVALID_GAME_ID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDetail {
    private Long id;

    @NotNull(message = INPUT_DATA_NULL_OR_BLANK)
    private Long matchId;

    @NotNull(message = INPUT_DATA_NULL_OR_BLANK)
    @Positive(message = INVALID_GAME_ID)
    private Integer gameId;

    private Long teamOneId;

    private Long teamTwoId;

    private String winningTeamName;

    private String mostValuablePlayerName;
}
