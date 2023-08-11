package com.lmluat.league.service;

import com.lmluat.league.dao.CoachDAO;
import com.lmluat.league.entity.CoachEntity;
import com.lmluat.league.exception.ErrorMessage;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.mapper.CoachMapper;
import com.lmluat.league.service.model.Coach;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;

@Stateless
public class CoachService {

    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    @Inject
    private CoachDAO coachDAO;

    @Inject
    private CoachMapper coachMapper;

    public Coach getById(Long id) throws ResourceNotFoundException {
        CoachEntity coachEntity = coachDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.COACH_NOT_FOUND, ErrorMessage.KEY_COACH_NOT_FOUND));
        return coachMapper.toDTO(coachEntity);
    }
}
