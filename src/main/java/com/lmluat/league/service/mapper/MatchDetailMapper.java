package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.MatchDetailEntity;
import com.lmluat.league.service.model.MatchDetail;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MatchDetailMapper extends EntityMapper<MatchDetailEntity, MatchDetail> {

    @Override
    MatchDetail toDTO(MatchDetailEntity entity);
}
