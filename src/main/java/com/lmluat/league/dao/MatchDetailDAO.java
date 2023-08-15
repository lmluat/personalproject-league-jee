package com.lmluat.league.dao;

import com.lmluat.league.entity.MatchDetailEntity;
import com.lmluat.league.entity.MatchEntity;
import com.lmluat.league.exception.ErrorMessage;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.model.Match;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class MatchDetailDAO extends BaseDAO<MatchDetailEntity> {
    public MatchDetailDAO() {
        super(MatchDetailEntity.class);
    }
    @Inject
    private MatchDAO matchDAO;

    public Optional<List<MatchDetailEntity>> findByMatchId(Long matchId) {
        CriteriaBuilder cq = em.getCriteriaBuilder();
        CriteriaQuery<MatchDetailEntity> cb = cq.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cb.from(MatchDetailEntity.class);

        cb.select(root);

        cb.where(cq.equal(root.get("match").get("id"), matchId));

        return Optional.of(em.createQuery(cb).getResultList());
    }

    public List<MatchDetailEntity> findByCriteria(Optional<Long> teamId, Optional<Long> tournamentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MatchDetailEntity> cq = cb.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cq.from(MatchDetailEntity.class);

        Predicate inTeamId = cb.equal(root.get("winningTeam").get("team").get("id"), teamId.orElse(null));
        Predicate inTournamentId = cb.equal(root.get("match").get("tournament").get("id"), tournamentId.orElse(null));

        cq.select(root).where(inTeamId, inTournamentId);

        return em.createQuery(cq).getResultList();
    }

    public List<MatchDetailEntity> findWinningGamesByTeamIdAndTournamentId(Optional<Long> teamId, Optional<Long> tournamentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MatchDetailEntity> cq = cb.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cq.from(MatchDetailEntity.class);


        Predicate winningGameListByTeamId = cb.equal(root.get("winningTeam").get("team").get("id"), teamId.orElse(null));
        Predicate inTournamentId = cb.equal(root.get("match").get("tournament").get("id"), tournamentId.orElse(null));
        Predicate condition = cb.and(winningGameListByTeamId, inTournamentId);

        cq.select(root).where(condition);

        return em.createQuery(cq).getResultList();
    }

    public List<MatchDetailEntity> findByTournamentId(Long tournamentId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MatchDetailEntity> cq = cb.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cq.from(MatchDetailEntity.class);

        Join<MatchDetailEntity, MatchEntity> matchJoin = root.join("match");

        Predicate inTournamentId = cb.equal(matchJoin.get("tournament").get("id"), tournamentId);

        cq.select(root).where(inTournamentId);

        return em.createQuery(cq).getResultList();
    }

    public List<MatchDetailEntity> findBetweenDates(Optional<Long> tournamentId,LocalDate fromDate, LocalDate toDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MatchDetailEntity> cq = cb.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cq.from(MatchDetailEntity.class);

        Join<MatchDetailEntity, MatchEntity> matchJoin = root.join("match");

        Predicate betweenDates = cb.between(matchJoin.get("date"), fromDate, toDate);
        Predicate inTournamentId = cb.equal(root.get("match").get("tournament").get("id"), tournamentId.orElse(null));

        cq.select(root).where(betweenDates, inTournamentId);

        return em.createQuery(cq).getResultList();
    }






}
