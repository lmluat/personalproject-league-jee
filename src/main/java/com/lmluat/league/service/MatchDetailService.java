package com.lmluat.league.service;

import com.lmluat.league.dao.MatchDAO;
import com.lmluat.league.dao.MatchDetailDAO;
import com.lmluat.league.dao.TeamDAO;
import com.lmluat.league.dao.TeamDetailDAO;
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
import java.util.Set;
import java.util.stream.Collectors;

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
    private TeamDetailDAO teamDetailDAO;

    @Inject
    private MatchDetailMapper matchDetailMapper;

    private final String defaultStatus = "Updating";

    public MatchDetail create(MatchDetail matchDetail) throws ResourceNotFoundException, InputValidationException {

        verifyMatchDetail(matchDetail);
        checkDuplicatedTeam(matchDetail.getTeamOneId(), matchDetail.getTeamTwoId());
        checkMatchIdAndGameId(matchDetail);

        MatchDetailEntity matchDetailEntity = MatchDetailEntity.builder()
                .match(matchDAO.findById(matchDetail.getMatchId()).orElseThrow(() -> new ResourceNotFoundException(MATCH_NOT_FOUND, KEY_MATCH_NOT_FOUND)))
                .gameId(matchDetail.getGameId())
                .teamOne(teamDetailDAO.findById(matchDetail.getTeamOneId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)))
                .teamTwo(teamDetailDAO.findById(matchDetail.getTeamTwoId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)))
                .build();

        setWinningTeamNameAndMVPPlayer(matchDetail, matchDetailEntity);

        return matchDetailMapper.toDTO(matchDetailDAO.create(matchDetailEntity));
    }


    public MatchDetail update(MatchDetail matchDetail, Long id) throws ResourceNotFoundException, InputValidationException {

        MatchDetailEntity matchDetailEntity = matchDetailDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(MATCH_DETAIL_NOT_FOUND, KEY_MATCH_DETAIL_NOT_FOUND));

        Long winningTeamId = matchDetail.getWinningTeamId();

        if (Objects.equals(winningTeamId, matchDetailEntity.getTeamOne().getId()) || Objects.equals(winningTeamId, matchDetailEntity.getTeamTwo().getId())) {
            matchDetailEntity.setWinningTeamName(teamDetailDAO.findById(winningTeamId).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)).getTeam().getTeamName());
        } else throw new InputValidationException(INVALID_INPUT_DATA, KEY_INVALID_INPUT_DATA);

        matchDetailDAO.update(matchDetailEntity);

        return matchDetailMapper.toDTO(matchDetailEntity);
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

    private void checkMatchIdAndGameId(MatchDetail matchDetail) throws InputValidationException {
        Integer gameId = matchDetail.getGameId();

        if (CollectionUtils.isNotEmpty(matchDetailDAO.findByMatchId(matchDetail.getMatchId()))) {
            for (MatchDetailEntity matchDetailEntity : matchDetailDAO.findByMatchId(matchDetail.getMatchId())) {
                System.out.println(matchDetailEntity.getGameId());
            }

            List<Integer> gameIdList = matchDetailDAO.findByMatchId(matchDetail.getMatchId()).stream().map(MatchDetailEntity::getGameId).collect(Collectors.toList());

            for (Integer existedGameId : gameIdList) {
                if (Objects.equals(existedGameId, gameId)) {
                    throw new InputValidationException("Game id is existed", "exception.input.validation.game.id.existed");
                }
            }
        }
    }

    private void setWinningTeamNameAndMVPPlayer(MatchDetail matchDetail, MatchDetailEntity matchDetailEntity) throws ResourceNotFoundException {
        if (matchDetail.getWinningTeamId() != null) {
            matchDetailEntity.setWinningTeamName(teamDetailDAO.findById(matchDetail.getWinningTeamId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)).getTeam().getTeamName());
        } else {
            matchDetailEntity.setWinningTeamName(defaultStatus);
            matchDetailEntity.setMostValuablePlayerName(defaultStatus);
        }

        if (matchDetail.getMostValuablePlayerName() == null) {
            matchDetailEntity.setMostValuablePlayerName(defaultStatus);
        } else {
            matchDetailEntity.setMostValuablePlayerName(matchDetail.getMostValuablePlayerName());
        }
    }

}
