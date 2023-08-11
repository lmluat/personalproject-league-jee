package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.MatchEntity;
import com.lmluat.league.service.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MatchMapper extends EntityMapper<MatchEntity, Match> {
}
