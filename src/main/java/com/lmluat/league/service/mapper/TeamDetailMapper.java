package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.TeamDetailEntity;
import com.lmluat.league.service.model.TeamDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamDetailMapper extends EntityMapper<TeamDetailEntity, TeamDetail> {
    @Override
    @Mapping(target = "coachName", source = "coach.ingameName")
    @Mapping(target = "teamName", source = "team.teamName")
    @Mapping(target = "tournamentName", source = "tournament.tournamentName")
    TeamDetail toDTO(TeamDetailEntity teamDetailEntity);
}
