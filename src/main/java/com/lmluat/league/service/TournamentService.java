package com.lmluat.league.service;

import com.lmluat.league.dao.TournamentDAO;

import com.lmluat.league.entity.TournamentEntity;
import com.lmluat.league.exception.ErrorMessage;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.mapper.TournamentMapper;
import com.lmluat.league.service.model.Tournament;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Stateless
public class TournamentService {
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();
    @Inject
    private TournamentDAO tournamentDAO;

    @Inject
    private TournamentMapper tournamentMapper;

    public List<Tournament> getAll() throws ResourceNotFoundException {
        return tournamentMapper.toDTOList(tournamentDAO.findAll());
    }

    public Tournament getById(Long id) throws ResourceNotFoundException {
        TournamentEntity tournamentEntity = tournamentDAO.findByTournamentId(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.TOURNAMENT_NOT_FOUND, ErrorMessage.KEY_TOURNAMENT_NOT_FOUND));

        return tournamentMapper.toDTO(tournamentEntity);
    }


    public Tournament create(Tournament tournament) throws InputValidationException {
        verifyTournament(tournament);

        TournamentEntity tournamentEntity = TournamentEntity.builder()
                .tournamentName(tournament.getTournamentName().trim())
                .sponsor(tournament.getSponsor().trim())
                .season(tournament.getSeason().trim())
                .startDate(LocalDate.from(tournament.getStartDate()))
                .endDate(LocalDate.from(tournament.getEndDate()))
                .build();

        return tournamentMapper.toDTO(tournamentDAO.create(tournamentEntity));
    }

    private void verifyTournament(Tournament tournament) throws InputValidationException {
        Set<ConstraintViolation<Tournament>> violations = validator.validate(tournament);

        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();

            for (ConstraintViolation<Tournament> violation : violations) {
                errorMessages.add(violation.getMessage());
            }

            throw new InputValidationException(ErrorMessage.KEY_TOURNAMENT_NOT_FOUND,
                    String.join(", ", errorMessages));
        }

        if (isDuplicatedName(tournament.getTournamentName().trim())) {
            throw new InputValidationException(ErrorMessage.TOURNAMENT_NAME_DUPLICATED, ErrorMessage.KEY_TOURNAMENT_NAME_DUPLICATED);
        }
    }

    private boolean isDuplicatedName(String tournamentName) {
        return tournamentDAO.findAll().stream().anyMatch(tournament -> tournament.getTournamentName().trim().equals(tournamentName));
    }


}

