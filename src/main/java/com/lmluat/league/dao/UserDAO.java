package com.lmluat.league.dao;

import com.lmluat.league.entity.UserEntity;

import javax.ejb.Stateless;

@Stateless
public class UserDAO extends BaseDAO<UserEntity> {
    public UserDAO() {
        super(UserEntity.class);
    }

    public UserEntity getUser(String username) {
        return em.createQuery("select u from UserEntity u WHERE LOWER(trim(both from u.name)) LIKE LOWER(trim(both from :username))", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
