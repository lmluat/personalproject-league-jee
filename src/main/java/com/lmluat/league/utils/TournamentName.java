package com.lmluat.league.utils;

import com.lmluat.league.exception.ErrorMessage;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {TournamentNameValidator.class})
public @interface TournamentName {
    String message() default ErrorMessage.TOURNAMENT_NAME_CONSTRAINT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 3;
    int max() default 1000;
}
