package com.lmluat.league.dao;

import com.lmluat.league.entity.MatchDetailEntity;
import com.lmluat.league.exception.ErrorMessage;
import com.lmluat.league.exception.ResourceNotFoundException;

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

    public Optional<List<MatchDetailEntity>> findByMatchId(Long matchId) {
        CriteriaBuilder cq = em.getCriteriaBuilder();
        CriteriaQuery<MatchDetailEntity> cb = cq.createQuery(MatchDetailEntity.class);

        Root<MatchDetailEntity> root = cb.from(MatchDetailEntity.class);

        cb.select(root);

        cb.where(cq.equal(root.get("match").get("id"), matchId));

        Optional<List<MatchDetailEntity>> matchDetailEntityList = Optional.of(em.createQuery(cb).getResultList());

        return matchDetailEntityList;
    }
}
