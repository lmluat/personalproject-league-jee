package com.lmluat.league.service;

import com.lmluat.league.dao.MatchDAO;
import com.lmluat.league.dao.MatchDetailDAO;
import com.lmluat.league.dao.TeamDetailDAO;
import com.lmluat.league.entity.MatchDetailEntity;

import com.lmluat.league.entity.TeamDetailEntity;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.mapper.MatchDetailMapper;
import com.lmluat.league.service.mapper.TeamDetailMapper;
import com.lmluat.league.service.model.MatchDetail;
import com.lmluat.league.service.model.TeamDetail;
import com.lmluat.league.service.model.custom.TeamDetailDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private TeamDetailDAO teamDetailDAO;

    @Inject
    private MatchDetailMapper matchDetailMapper;

    @Inject
    private TeamDetailMapper teamDetailMapper;
    private final String defaultStatus = "Updating";

    public MatchDetail create(MatchDetail matchDetail) throws ResourceNotFoundException, InputValidationException {

        verifyMatchDetail(matchDetail);
        if (matchDetail.getTeamOneId() != null || matchDetail.getTeamTwoId() != null) {
            checkDuplicatedTeam(matchDetail.getTeamOneId(), matchDetail.getTeamTwoId());
        }

        MatchDetailEntity matchDetailEntity = MatchDetailEntity.builder()
                .match(matchDAO.findById(matchDetail.getMatchId()).orElseThrow(() -> new ResourceNotFoundException(MATCH_NOT_FOUND, KEY_MATCH_NOT_FOUND)))
                .teamOne(teamDetailDAO.findById(matchDetail.getTeamOneId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)))
                .teamTwo(teamDetailDAO.findById(matchDetail.getTeamTwoId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)))
                .build();

        setWinningTeamNameAndMVPPlayer(matchDetail, matchDetailEntity);

        if (matchDetail.getWinningTeamId() != null) {
            matchDetail.setWinningTeamName(matchDetailEntity.getWinningTeam().getTeam().getTeamName());
        } else {
            matchDetail.setWinningTeamName(defaultStatus);
        }

        return matchDetailMapper.toDTO(matchDetailDAO.create(matchDetailEntity));
    }


    public MatchDetail update(MatchDetail matchDetail, Long id) throws ResourceNotFoundException, InputValidationException {

        MatchDetailEntity matchDetailEntity = matchDetailDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(MATCH_DETAIL_NOT_FOUND, KEY_MATCH_DETAIL_NOT_FOUND));

        verifyUpdateMatchDetail(matchDetail);

        setWinningTeamAndMVPPlayerForUpdate(matchDetail, matchDetailEntity);

        checkDuplicatedTeamAfterUpdate(matchDetailEntity);

        return matchDetailMapper.toDTO(matchDetailDAO.update(matchDetailEntity));
    }

    private void checkDuplicatedTeamAfterUpdate(MatchDetailEntity matchDetailEntity) throws InputValidationException {
        Long teamOneId = matchDetailEntity.getTeamOne().getId();
        Long teamTwoId = matchDetailEntity.getTeamTwo().getId();
        if (teamOneId.equals(teamTwoId)) {
            throw new InputValidationException(TWO_TEAM_DUPLICATED, KEY_TWO_TEAM_DUPLICATED);
        }
    }

    private void verifyUpdateMatchDetail(MatchDetail matchDetail) throws InputValidationException {
        if (matchDetail.getMatchId() != null) {
            throw new InputValidationException("Match Id cannot be changed", "exception.input.validation.match.id.change");
        }
    }


    private void checkDuplicatedTeam(Long teamOneId, Long teamTwoId) throws InputValidationException {
        if (Objects.equals(teamOneId, teamTwoId)) {
            throw new InputValidationException(TWO_TEAM_DUPLICATED, KEY_TWO_TEAM_DUPLICATED);
        }
    }

    private void verifyMatchDetail(MatchDetail matchDetail) throws InputValidationException {
        if (matchDetailDAO.findByMatchId(matchDetail.getMatchId()).get().size() > 0) {
            throw new InputValidationException("Match detail for match is existed", "exception.input.validation.match.detail.exists");
        }
        Long tournamentId = matchDAO.findById(matchDetail.getMatchId()).get().getTournament().getId();

        List<Long> teamDetailIdList = teamDetailDAO.findByTournamentId(tournamentId).stream().map(TeamDetailEntity::getId).collect(Collectors.toList());

        if (!teamDetailIdList.contains(matchDetail.getTeamOneId()) || !teamDetailIdList.contains(matchDetail.getTeamTwoId())) {
            throw new InputValidationException("Team is invalid", "exception.input.validation.team.invalid");
        }

        Set<ConstraintViolation<MatchDetail>> violations = validator.validate(matchDetail);

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void setWinningTeamNameAndMVPPlayer(MatchDetail matchDetail, MatchDetailEntity matchDetailEntity) throws ResourceNotFoundException, InputValidationException {
        Optional<Long> winningTeamId = Optional.ofNullable(matchDetail.getWinningTeamId());
        Optional<Long> teamOneId = Optional.ofNullable(matchDetail.getTeamOneId());
        Optional<Long> teamTwoId = Optional.ofNullable(matchDetail.getTeamTwoId());

        if (winningTeamId.isPresent()) {
            if (Objects.equals(winningTeamId, teamOneId) || Objects.equals(winningTeamId, teamTwoId)) {
                matchDetailEntity.setWinningTeam(teamDetailDAO.findById(matchDetail.getWinningTeamId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)));
                matchDetail.setWinningTeamName(matchDetailEntity.getWinningTeam().getTeam().getTeamName());
            } else {
                throw new InputValidationException(INVALID_INPUT_DATA, KEY_INVALID_INPUT_DATA);
            }
        }

        if (matchDetail.getMostValuablePlayerName() == null) {
            matchDetailEntity.setMostValuablePlayerName(defaultStatus);
        } else {
            matchDetailEntity.setMostValuablePlayerName(matchDetail.getMostValuablePlayerName());
        }
    }

    private void setWinningTeamAndMVPPlayerForUpdate(MatchDetail matchDetail, MatchDetailEntity matchDetailEntity) throws ResourceNotFoundException, InputValidationException {
        Optional<Long> winningTeamId = Optional.ofNullable(matchDetail.getWinningTeamId());
        Optional<Long> teamOneId = Optional.ofNullable(matchDetail.getTeamOneId());
        Optional<Long> teamTwoId = Optional.ofNullable(matchDetail.getTeamTwoId());

        Long teamOneIdCurrent = matchDetailEntity.getTeamOne().getId();
        Long teamTwoIdCurrent = matchDetailEntity.getTeamTwo().getId();

        List<Optional<Long>> teamIdList = List.of(teamOneId, teamTwoId);

        if (matchDetail.getMostValuablePlayerName() != null) {
            matchDetailEntity.setMostValuablePlayerName(matchDetail.getMostValuablePlayerName());
        }

        if (winningTeamId.isPresent() && teamOneId.isPresent() && teamTwoId.isPresent()) {
            if (!teamIdList.contains(winningTeamId)) {
                throw new InputValidationException("Winning team ID is invalid", "exception.input.validation.team.id.invalid");
            } else {
                matchDetailEntity.setTeamOne(teamDetailDAO.findById(teamOneId.get()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)));
                matchDetailEntity.setTeamTwo(teamDetailDAO.findById(teamTwoId.get()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)));
                matchDetailEntity.setWinningTeam(teamDetailDAO.findById(winningTeamId.get()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)));
            }
            return;
        }

        if (winningTeamId.isPresent()) {
            if (winningTeamId.get().equals(teamOneIdCurrent) || winningTeamId.get().equals(teamTwoIdCurrent)) {
                matchDetailEntity.setWinningTeam(teamDetailDAO.findById(matchDetail.getWinningTeamId()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)));
                matchDetail.setWinningTeamName(matchDetailEntity.getWinningTeam().getTeam().getTeamName());
            } else {
                throw new InputValidationException("Winning match id is invalid", "exception.input.validation.match.id.invalid");
            }
        }

        if (teamOneId.isPresent()) {
            if (!Objects.equals(teamOneIdCurrent, teamOneId.get())) {
                matchDetailEntity.setTeamOne(teamDetailDAO.findById(teamOneId.get()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)));
                matchDetailEntity.setWinningTeam(null);
            }
        }

        if (teamTwoId.isPresent()) {
            if (!Objects.equals(teamTwoIdCurrent, teamTwoId.get())) {
                matchDetailEntity.setTeamTwo(teamDetailDAO.findById(teamTwoId.get()).orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND)));
                matchDetailEntity.setWinningTeam(null);
            }
        }
    }

    public List<TeamDetailDTO> getRankingTableByTournamentId(Long tournamentId) {

        List<TeamDetail> teamDetailList = teamDetailMapper.toDTOList(teamDetailDAO.findByTournamentId(tournamentId));

        Map<TeamDetail, Optional<Integer>> teamWinningGameList = new HashMap<TeamDetail, Optional<Integer>>();

        for (TeamDetail teamDetail : teamDetailList) {
            Integer teamWinningGameTotal = matchDetailDAO.findRankingTableInTournament(Optional.ofNullable(teamDetail.getId()), Optional.ofNullable(tournamentId)).size();
            teamWinningGameList.put(teamDetail, Optional.of(teamWinningGameTotal));
        }
        List<TeamDetailDTO> teamDetailDTOList = new ArrayList<>();

        teamWinningGameList.forEach((teamDetail, winningGameTotal) -> {
            TeamDetailDTO teamDetailDTO = new TeamDetailDTO();

            teamDetailDTO.setTeamName(teamDetail.getTeamName());
            teamDetailDTO.setWins(winningGameTotal.get());

            teamDetailDTOList.add(teamDetailDTO);
        });

        return teamDetailDTOList.stream().sorted(Comparator.comparing(TeamDetailDTO::getWins).reversed()).collect(Collectors.toList());
    }

    public List<MatchDetail> getBetweenDates(Optional<Long> tournamentId, LocalDate startDate, LocalDate endDate) {
        return matchDetailMapper.toDTOList(matchDetailDAO.findBetweenDates(tournamentId, startDate, endDate));
    }

    public List<MatchDetail> getByTournamentName(String tournamentName){
        return matchDetailMapper.toDTOList(matchDetailDAO.findByTournamentName(tournamentName.trim().toLowerCase()));
    }

    public List<MatchDetail> getByCriteria(Optional<Long> teamId, Optional<Long> tournamentId){
        return matchDetailMapper.toDTOList(matchDetailDAO.findByCriteria(teamId, tournamentId));
    }


}

