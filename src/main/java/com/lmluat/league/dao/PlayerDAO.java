package com.lmluat.league.dao;

import com.lmluat.league.entity.PlayerEntity;

import javax.ejb.Stateless;

@Stateless
public class PlayerDAO extends BaseDAO<PlayerEntity> {
    public PlayerDAO() {
        super(PlayerEntity.class);
    }
}
