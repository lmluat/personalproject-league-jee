package com.lmluat.league.service;

import com.lmluat.league.dao.TeamDAO;
import com.lmluat.league.entity.TeamEntity;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.mapper.TeamMapper;
import com.lmluat.league.service.model.Team;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

import static com.lmluat.league.exception.ErrorMessage.*;

@Stateless
public class TeamService {
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    @Inject
    private TeamDAO teamDAO;

    @Inject
    private TeamMapper teamMapper;

    public List<Team> getAll() throws ResourceNotFoundException {
        return teamMapper.toDTOList(teamDAO.findAll());
    }

    public Team getById(Long id) throws ResourceNotFoundException {
        TeamEntity teamEntity = teamDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND, KEY_TEAM_NOT_FOUND));
        return teamMapper.toDTO(teamEntity);
    }

    public Team create(Team team) throws InputValidationException {
        verifyTeam(team);

        TeamEntity teamEntity = TeamEntity.builder()
                .teamName(team.getTeamName().trim())
                .location(team.getLocation().trim())
                .build();

        return teamMapper.toDTO(teamEntity);
    }

    private void verifyTeam(Team team) throws InputValidationException {
        if (isDuplicatedTeamName(team.getTeamName().trim())) {
            throw new InputValidationException(TEAM_NAME_DUPLICATED, KEY_TEAM_NAME_DUPLICATED);
        }
    }

    private boolean isDuplicatedTeamName(String teamName) {
        return teamDAO.findAll().stream().anyMatch(team -> team.getTeamName().equals(teamName));
    }

}
