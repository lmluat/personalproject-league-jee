package com.lmluat.league.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.lmluat.league.entity.RoleEnum;
import com.lmluat.league.entity.StatusEnum;
import com.lmluat.league.entity.UserEntity;
import com.lmluat.league.exception.AuthorizationException;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.service.AuthService;
import com.lmluat.league.service.UserService;
import com.lmluat.league.utils.JwtRequest;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Stateless
public class JwtUtils {
    private static final long EXPIRATION_LIMIT_IN_MINUTES = 72000000;

    private static final String SECRET_KEY = "league-secret";

    private static final String ISSUER = "League";
    private static final String ROLE = "ROLE";
    private static final String NAME = "NAME";
    private static final String BEARER = "Bearer";
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    @Inject
    private AuthService authenticationService;

    @Inject
    private UserService userService;

    public String generateToken(JwtRequest jwtRequest) throws InputValidationException, AuthenticationException {
        if (!authenticationService.authenticateUser(jwtRequest)) {
            throw new InputValidationException("input.validation.password.username.invalid", "Username or password is wrong");
        }
        String token;

        RoleEnum roleEnum = userService.getUser(jwtRequest.getName()).getRole();

        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date())
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim(NAME,jwtRequest.getName())
                    .withClaim(ROLE, String.valueOf(roleEnum))
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_LIMIT_IN_MINUTES))
                    .sign(algorithm);
        } catch (JWTCreationException | IllegalArgumentException e) {
            throw new AuthenticationException();
        }
        return token;
    }

    public void validateJwtToken(String token) throws AuthorizationException {
        if (token == null) {
            throw new AuthorizationException("KEY_UNAUTHORIZED_ACCESS", "UNAUTHORIZED_ACCESS");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER).build();
            verifier.verify(token.substring(BEARER.length()).trim());
        } catch (JWTVerificationException | IllegalArgumentException e) {
            throw new AuthorizationException("KEY_UNAUTHORIZED_ACCESS", "UNAUTHORIZED_ACCESS");
        }
    }

    public JwtResponse generateJwtResponse(JwtRequest jwtRequest) throws AuthorizationException, InputValidationException, AuthenticationException {
        verifyJwtRequest(jwtRequest);
        String name = jwtRequest.getName().trim();
        String token = generateToken(jwtRequest);
        UserEntity user = userService.getUser(name);
        RoleEnum roleEnum = user.getRole();
        StatusEnum status = user.getStatus();

        return new JwtResponse(token, name, roleEnum, status);
    }

    public RoleEnum getRoleFromToken(String authorization) {
        String token = authorization.substring(BEARER.length()).trim();
        DecodedJWT decodedJWT = JWT.decode(token);
        return RoleEnum.valueOf(decodedJWT.getClaim(ROLE).asString());
    }

    public String getNameFromToken(String authorization) {
        String token = authorization.substring(BEARER.length()).trim();
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim(NAME).asString();
    }

    public Date getExpireTokenTime(String authorization) {
        String token = authorization.substring(BEARER.length()).trim();
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt();
    }

    private boolean isActive(StatusEnum status) {
        return status.equals(StatusEnum.ACTIVE);
    }

    public void verifyJwtRequest(JwtRequest jwtRequest) throws AuthorizationException {
        Set<ConstraintViolation<JwtRequest>> violations = validator.validate(jwtRequest);

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        StatusEnum statusEnum = userService.getUser(jwtRequest.getName()).getStatus();
        if (!isActive(statusEnum)) {
            throw new AuthorizationException("KEY_FORBIDDEN_ACCESS","FORBIDDEN_ACCESS");
        }
    }
}

