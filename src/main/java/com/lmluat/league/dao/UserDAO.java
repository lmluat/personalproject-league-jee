package com.lmluat.league.dao;

import com.lmluat.league.entity.UserEntity;

import javax.ejb.Stateless;

@Stateless
public class UserDAO extends BaseDAO<UserEntity> {
    public UserDAO() {
        super(UserEntity.class);
    }
}
