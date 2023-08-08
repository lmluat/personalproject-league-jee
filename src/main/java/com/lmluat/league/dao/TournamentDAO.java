package com.lmluat.league.dao;

import com.lmluat.league.entity.TournamentEntity;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class TournamentDAO extends BaseDAO<TournamentEntity> {
    public TournamentDAO() {
        super(TournamentEntity.class);
    }

    public Optional<TournamentEntity> findByTournamentId(Long id) {
        return Optional.ofNullable(em.find(TournamentEntity.class, id));
    }

}
