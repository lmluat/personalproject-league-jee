package com.lmluat.league.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TournamentNameValidator implements ConstraintValidator<TournamentName, String> {
    private static final String NAME_PREFIX = "minhluat-";

    @Override
    public void initialize(TournamentName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String tournamentName, ConstraintValidatorContext constraintValidatorContext) {

        if (tournamentName == null || tournamentName.trim().isEmpty()) {
            return false;
        }

        if (!tournamentName.startsWith(NAME_PREFIX)) {
            return false;
        }

        String nameWithoutPrefix = tournamentName.substring(NAME_PREFIX.length());
        return nameWithoutPrefix.trim().length() >= 3 && nameWithoutPrefix.trim().length() <= 1000;
    }
}
