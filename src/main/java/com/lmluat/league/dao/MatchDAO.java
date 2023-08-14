package com.lmluat.league.dao;

import com.lmluat.league.entity.MatchEntity;
import com.lmluat.league.exception.ErrorMessage;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.model.Match;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Stateless
public class MatchDAO extends BaseDAO<MatchEntity> {
    public MatchDAO() {
        super(MatchEntity.class);
    }

    public List<MatchEntity> findByTournamentId(Long tournamentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MatchEntity> cq = cb.createQuery(MatchEntity.class);

        Root<MatchEntity> root = cq.from(MatchEntity.class);

        cq.select(root);

        Predicate inTournamentId = cb.equal(root.get("tournament").get("id"), tournamentId);

        cq.where(inTournamentId);

        return em.createQuery(cq).getResultList();
    }

    public List<MatchEntity> findByBetweenDates(LocalDate fromDate, LocalDate toDate) {

        return em.createQuery("select m from MatchEntity m where m.matchDate between :fromDate and :toDate", MatchEntity.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();

    }
}
