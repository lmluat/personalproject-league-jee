package com.lmluat.league.dao;

import com.lmluat.league.entity.MatchDetailEntity;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class MatchDetailDAO extends BaseDAO<MatchDetailEntity> {
    public MatchDetailDAO() {
        super(MatchDetailEntity.class);
    }

    public List<Integer> findGameIdListById(Optional<Long> matchId) {

        CriteriaBuilder cq = em.getCriteriaBuilder();
        CriteriaQuery<MatchDetailEntity> cb = cq.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cb.from(MatchDetailEntity.class);

        cb.select(root);

        matchId.ifPresent(m -> cq.equal(root.get("match").get("id"), m));

        return em.createQuery(cb).getResultList().stream().map(MatchDetailEntity::getGameId).collect(Collectors.toList());

    }
}
