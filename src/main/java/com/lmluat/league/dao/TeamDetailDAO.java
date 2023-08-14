package com.lmluat.league.dao;

import com.lmluat.league.entity.TeamDetailEntity;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class TeamDetailDAO extends BaseDAO<TeamDetailEntity> {
    public TeamDetailDAO() {
        super(TeamDetailEntity.class);
    }

    public List<TeamDetailEntity> findByTournamentId(Long tournamentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<TeamDetailEntity> cq = cb.createQuery(TeamDetailEntity.class);

        Root<TeamDetailEntity> root = cq.from(TeamDetailEntity.class);

        cq.select(root);

        Predicate inTournamentId = cb.equal(root.get("tournament").get("id"), tournamentId);

        cq.where(inTournamentId);

        return em.createQuery(cq).getResultList();
    }
}
