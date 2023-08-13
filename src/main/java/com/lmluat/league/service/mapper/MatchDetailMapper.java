package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.MatchDetailEntity;
import com.lmluat.league.service.model.MatchDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MatchDetailMapper extends EntityMapper<MatchDetailEntity, MatchDetail> {

    @Override
    @Mapping(target = "teamOneName",source = "teamOne.team.teamName")
    @Mapping(target = "teamTwoName",source = "teamTwo.team.teamName")
    @Mapping(target = "matchId",source = "match.id")
    @Mapping(target = "winningTeamName",source = "winningTeam.team.teamName")
    MatchDetail toDTO(MatchDetailEntity entity);

    @Override
    MatchDetailEntity toEntity(MatchDetail DTO);
}
