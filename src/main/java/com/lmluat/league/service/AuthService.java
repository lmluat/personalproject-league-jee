package com.lmluat.league.service;

import com.lmluat.league.entity.UserEntity;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.service.model.UserDTO;
import com.lmluat.league.utils.JwtRequest;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.Base64;

@Stateless
public class AuthService {
    @Inject
    private UserService userService;

    public boolean authenticateUser(JwtRequest request) throws InputValidationException {

        UserEntity existedUser = userService.getUser(request.getName());
        System.out.println(existedUser.getPassword());
        String password = decryptBase64Password(request.getPassword());

        return BCrypt.checkpw(password, existedUser.getPassword());
    }

    private String decryptBase64Password(String password) throws InputValidationException {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(password);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            throw new InputValidationException("input.validation.password.username.invalid", "Username or password is wrong");
        }
    }
}
