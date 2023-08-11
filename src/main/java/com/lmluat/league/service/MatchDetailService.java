package com.lmluat.league.service;

import com.lmluat.league.dao.MatchDAO;
import com.lmluat.league.dao.MatchDetailDAO;
import com.lmluat.league.dao.TeamDAO;
import com.lmluat.league.entity.MatchDetailEntity;

import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.mapper.MatchDetailMapper;
import com.lmluat.league.service.model.MatchDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.lmluat.league.exception.ErrorMessage.*;

@Stateless
public class MatchDetailService {
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    @Inject
    private MatchDetailDAO matchDetailDAO;

    @Inject
    private MatchDAO matchDAO;
    @Inject
    private TeamDAO teamDAO;

    @Inject
    private MatchDetailMapper matchDetailMapper;

    private final Integer gameIdDefault = 1;
    private final String defaultStatus = "Updating";

    public MatchDetail create(MatchDetail matchDetail) throws ResourceNotFoundException, InputValidationException {

        verifyMatchDetail(matchDetail);
        checkDuplicatedTeam(matchDetail.getTeamOneId(), matchDetail.getTeamTwoId());

        checkGameId(matchDetail);

        MatchDetailEntity matchDetailEntity = MatchDetailEntity.builder()
                .match(matchDAO.findById(matchDetail.getMatchId()).orElseThrow(() -> new ResourceNotFoundException(MATCH_NOT_FOUND, KEY_MATCH_NOT_FOUND)))
                .gameId(matchDetail.getGameId())
                .teamOne(teamDAO.findById(matchDetail.getTeamOneId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)))
                .teamTwo(teamDAO.findById(matchDetail.getTeamTwoId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)))
                .build();

        matchDetailDAO.create(matchDetailEntity);

        matchDetail.setMostValuablePlayerName(defaultStatus);
        matchDetail.setWinningTeamName(defaultStatus);

        return matchDetail;
    }

    private void checkDuplicatedTeam(Long teamOneId, Long teamTwoId) throws InputValidationException {
        if (Objects.equals(teamOneId, teamTwoId)) {
            throw new InputValidationException(TWO_TEAM_DUPLICATED, KEY_TWO_TEAM_DUPLICATED);
        }
    }

    private void verifyMatchDetail(MatchDetail matchDetail) throws InputValidationException {
        Set<ConstraintViolation<MatchDetail>> violations = validator.validate(matchDetail);

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        Integer gameId = matchDetail.getGameId();

        if (gameId > 3) {
            throw new InputValidationException(INVALID_INPUT_DATA, KEY_INVALID_INPUT_DATA);
        }

    }

    private void checkGameId(MatchDetail matchDetail) throws InputValidationException {
        Integer gameId = matchDetail.getGameId();

        List<Integer> gameIdList = matchDetailDAO.findGameIdListById(Optional.ofNullable(matchDetail.getMatchId()));
        System.out.println(gameIdList);

        for (Integer gameId2 : gameIdList) {
            if (Objects.equals(gameId2, gameId)) {
                throw new InputValidationException(INVALID_INPUT_DATA, KEY_INVALID_INPUT_DATA);
            }
        }
    }
}
