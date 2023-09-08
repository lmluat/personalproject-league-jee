package com.lmluat.league.service;

import com.lmluat.league.dao.UserDAO;
import com.lmluat.league.entity.RoleEnum;
import com.lmluat.league.entity.StatusEnum;
import com.lmluat.league.entity.UserEntity;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.service.mapper.UserMapper;
import com.lmluat.league.service.model.UserDTO;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.registry.infomodel.User;
@Stateless
public class UserService {
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    @Inject
    private UserDAO userDAO;

    @Inject
    private UserMapper userMapper;

    public UserDTO create(UserDTO user) throws InputValidationException, IllegalArgumentException {
        UserEntity userEntity = UserEntity.builder()
                .name(user.getName().trim())
                .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .status(StatusEnum.ACTIVE)
                .role(RoleEnum.ROLE_USER)
                .build();

        return userMapper.toDTO(userDAO.create(userEntity));
    }

    public UserEntity getUser(String username) {
        return userDAO.getUser(username);
    }


}
