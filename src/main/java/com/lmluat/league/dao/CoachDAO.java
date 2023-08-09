package com.lmluat.league.dao;

import com.lmluat.league.entity.CoachEntity;

import javax.ejb.Stateless;

@Stateless
public class CoachDAO extends BaseDAO<CoachEntity>{
    public CoachDAO() {
        super(CoachEntity.class);
    }
}
