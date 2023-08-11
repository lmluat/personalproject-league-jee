package com.lmluat.league.service.mapper;

import com.lmluat.league.entity.CoachEntity;
import com.lmluat.league.service.model.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CoachMapper extends EntityMapper<CoachEntity, Coach> {
}
