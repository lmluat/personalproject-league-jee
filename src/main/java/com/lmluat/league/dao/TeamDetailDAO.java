package com.lmluat.league.dao;

import com.lmluat.league.entity.TeamDetailEntity;

import javax.ejb.Stateless;

@Stateless
public class TeamDetailDAO extends BaseDAO<TeamDetailEntity>{
    public TeamDetailDAO() {
        super(TeamDetailEntity.class);
    }
}
