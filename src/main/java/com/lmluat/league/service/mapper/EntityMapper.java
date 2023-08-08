package com.lmluat.league.service.mapper;


import java.util.List;

public interface EntityMapper<E, D> {

    D toDTO(E entity);

    List<D> toDTOList(List<E> entityList);

    E toEntity(D DTO);

    List<E> toEntityList(List<D> DTOList);
}