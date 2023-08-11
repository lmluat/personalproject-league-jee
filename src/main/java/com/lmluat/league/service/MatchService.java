package com.lmluat.league.service;

import com.lmluat.league.dao.MatchDAO;
import com.lmluat.league.dao.TournamentDAO;
import com.lmluat.league.entity.MatchEntity;
import com.lmluat.league.service.mapper.MatchMapper;
import com.lmluat.league.service.model.Match;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Stateless
public class MatchService {
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    @Inject
    private MatchDAO matchDAO;

    @Inject
    private MatchMapper matchMapper;

    @Inject
    private TournamentDAO tournamentDAO;

    private final String location = "GG stadium";

    private void verifyMatch(Match match) {

        Set<ConstraintViolation<Match>> violations = validator.validate(match);

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

    }
}
