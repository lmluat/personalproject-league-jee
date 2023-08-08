package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.TournamentEntity;
import com.lmluat.league.service.model.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TournamentMapper extends EntityMapper<TournamentEntity, Tournament> {

    @Mapping(target = "id", ignore = true)
    void updateFromDTO(Tournament tournament, @MappingTarget TournamentEntity tournamentEntity);
}
