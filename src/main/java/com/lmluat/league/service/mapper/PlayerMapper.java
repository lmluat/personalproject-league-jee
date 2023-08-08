package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.PlayerEntity;
import com.lmluat.league.service.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlayerMapper extends EntityMapper<PlayerEntity, Player> {
}
