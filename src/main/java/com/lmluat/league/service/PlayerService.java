package com.lmluat.league.service;

import com.lmluat.league.dao.PlayerDAO;
import com.lmluat.league.entity.PlayerEntity;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.service.mapper.PlayerMapper;
import com.lmluat.league.service.model.Player;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;

import static com.lmluat.league.exception.ErrorMessage.*;

@Stateless
public class PlayerService {
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();
    @Inject
    private PlayerDAO playerDAO;

    @Inject
    private PlayerMapper playerMapper;

    public Player create(Player player) throws InputValidationException {
        verifyPlayer(player);

        PlayerEntity playerEntity = PlayerEntity.builder()
                .firstName(player.getFirstName().trim())
                .lastName(player.getLastName().trim())
                .dob(player.getDob())
                .homeTown(player.getHomeTown().trim())
                .build();

        return playerMapper.toDTO(playerDAO.create(playerEntity));
    }

    private void verifyPlayer(Player player) throws InputValidationException {
        if (isDuplicatedIngameName(player.getIngameName().trim())) {
            throw new InputValidationException(PLAYER_INGAME_NAME_DUPLICATED, KEY_PLAYER_INGAME_NAME_DUPLICATED);
        }
    }

    private boolean isDuplicatedIngameName(String ingameName) {
        return playerDAO.findAll().stream().anyMatch(player -> player.getIngameName().equals(ingameName));
    }
}
