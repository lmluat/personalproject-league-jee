package com.lmluat.league.dao;

import com.lmluat.league.entity.MatchDetailEntity;
import com.lmluat.league.entity.MatchEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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

       Predicate finalPredicate = cb.or(inTeamId, inTournamentId);

        cq.select(root).where(finalPredicate);

        return em.createQuery(cq).getResultList();
    }

    public List<MatchDetailEntity> findRankingTableInTournament(Optional<Long> teamId, Optional<Long> tournamentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MatchDetailEntity> cq = cb.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cq.from(MatchDetailEntity.class);


        Predicate winningGameListByTeamId = cb.equal(root.get("winningTeam").get("team").get("id"), teamId.orElse(null));
        Predicate inTournamentId = cb.equal(root.get("match").get("tournament").get("id"), tournamentId.orElse(null));
        Predicate condition = cb.and(winningGameListByTeamId, inTournamentId);

        cq.select(root).where(condition);

        return em.createQuery(cq).getResultList();
    }

    public List<MatchDetailEntity> findBetweenDates(Optional<Long> tournamentId, LocalDate fromDate, LocalDate toDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MatchDetailEntity> cq = cb.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cq.from(MatchDetailEntity.class);

        Join<MatchDetailEntity, MatchEntity> matchJoin = root.join("match");

        Predicate betweenDates = cb.between(matchJoin.get("date"), fromDate, toDate);
        Predicate inTournamentId = cb.equal(root.get("match").get("tournament").get("id"), tournamentId.orElse(null));

        cq.select(root).where(betweenDates, inTournamentId);

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

    public List<MatchDetailEntity> findByTournamentName(String tournamentName) {
        return em.createQuery(
                        "SELECT md FROM MatchDetailEntity md " +
                                "JOIN MatchEntity m ON m.id = md.match.id " +
                                "JOIN TournamentEntity t ON t.id = m.tournament.id " +
                                "WHERE LOWER(t.tournamentName) LIKE :tournamentName", MatchDetailEntity.class)
                .setParameter("tournamentName", "%" + tournamentName + "%")
                .getResultList();
    }


    public List<MatchDetailEntity> findFromDateToDate(LocalDate fromDate, LocalDate toDate) {
        Query query = em.createQuery(
                "SELECT mde FROM MatchDetailEntity mde " +
                        "JOIN MatchEntity match " +
                        "WHERE match.date BETWEEN :fromDate AND :toDate ", MatchDetailEntity.class);

        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        return query.getResultList();
    }

    public List<MatchDetailEntity> findByWinningTeamName(String winningTeamName) {

        return em.createQuery("SELECT mde FROM MatchDetailEntity mde " +
                "JOIN TeamDetailEntity team ON team.id = mde.winningTeam " +
                "JOIN FETCH mde.winningTeam team " +
                "WHERE team.teamName LIKE :winningTeamName", MatchDetailEntity.class)
                .setParameter("winningTeamName", "%" + winningTeamName + "%")
                .getResultList();

    }
}
