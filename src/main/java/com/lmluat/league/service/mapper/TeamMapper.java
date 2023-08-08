package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.TeamEntity;
import com.lmluat.league.service.model.Team;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamMapper extends EntityMapper<TeamEntity, Team> {

    @Mapping(target = "id", ignore = true)
    void updateFromDTO(Team team, @MappingTarget TeamEntity teamEntity);
}
