package com.lmluat.league.dao;

import com.lmluat.league.entity.MatchEntity;

import javax.ejb.Stateless;

@Stateless
public class MatchDAO extends BaseDAO<MatchEntity> {
    public MatchDAO() {
        super(MatchEntity.class);
    }
}
