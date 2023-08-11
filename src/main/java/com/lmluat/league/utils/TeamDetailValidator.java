package com.lmluat.league.utils;

import com.lmluat.league.service.model.TeamDetail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TeamDetailValidator implements ConstraintValidator<ValidTeamDetail, TeamDetail> {

    @Override
    public void initialize(ValidTeamDetail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TeamDetail teamDetail, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
