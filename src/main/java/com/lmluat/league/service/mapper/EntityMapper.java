package com.lmluat.league.service.mapper;


import com.lmluat.league.entity.TeamDetailEntity;
import com.lmluat.league.service.model.custom.TeamDetailDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface EntityMapper<E, D> {

    D toDTO(E entity);

    List<D> toDTOList(List<E> entityList);

    E toEntity(D DTO);

    List<E> toEntityList(List<D> DTOList);
}