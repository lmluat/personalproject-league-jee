package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.TournamentEntity;
import com.lmluat.league.entity.UserEntity;
import com.lmluat.league.service.model.Tournament;
import com.lmluat.league.service.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends EntityMapper<UserEntity, UserDTO> {
    @Override
    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(UserEntity entity);
}
