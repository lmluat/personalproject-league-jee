package com.lmluat.league.dao;

import com.lmluat.league.entity.TeamEntity;

import javax.ejb.Stateless;

@Stateless
public class TeamDAO extends BaseDAO<TeamEntity> {
    public TeamDAO() {
        super(TeamEntity.class);
    }
}
