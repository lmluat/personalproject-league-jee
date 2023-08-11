package com.lmluat.league.service;

import com.lmluat.league.dao.CoachDAO;
import com.lmluat.league.dao.TeamDAO;
import com.lmluat.league.dao.TeamDetailDAO;
import com.lmluat.league.dao.TournamentDAO;
import com.lmluat.league.entity.TeamDetailEntity;
import com.lmluat.league.exception.ErrorMessage;
import com.lmluat.league.exception.InputValidationException;
import com.lmluat.league.exception.ResourceNotFoundException;
import com.lmluat.league.service.mapper.TeamDetailMapper;
import com.lmluat.league.service.model.TeamDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Stateless
public class TeamDetailService {
    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    @Inject
    private TeamDetailDAO teamDetailDAO;

    @Inject
    private TournamentDAO tournamentDAO;

    @Inject
    private TeamDAO teamDAO;

    @Inject
    private CoachDAO coachDAO;

    @Inject
    private TeamDetailMapper teamDetailMapper;

    @PersistenceContext
    private EntityManager em;

    public List<TeamDetail> getAll() {
        return teamDetailMapper.toDTOList(teamDetailDAO.findAll());
    }

    public TeamDetail create(TeamDetail teamDetail) throws InputValidationException {
        verifyTeamDetail(teamDetail);

        TeamDetailEntity teamDetailEntity = TeamDetailEntity.builder()
                .tournament(tournamentDAO.findById(teamDetail.getTournamentId()).orElseThrow(() -> new InputValidationException(ErrorMessage.TOURNAMENT_NOT_FOUND, ErrorMessage.KEY_TOURNAMENT_NOT_FOUND)))
                .team(teamDAO.findById(teamDetail.getTeamId()).orElseThrow(() -> new InputValidationException(ErrorMessage.TEAM_NOT_FOUND, ErrorMessage.KEY_TEAM_NOT_FOUND)))
                .coach(coachDAO.findById(teamDetail.getCoachId()).orElseThrow(() -> new InputValidationException(ErrorMessage.COACH_NOT_FOUND, ErrorMessage.KEY_COACH_NOT_FOUND)))
                .sponsor(teamDetail.getSponsor())
                .build();

        return teamDetailMapper.toDTO(teamDetailEntity);
    }

    private void verifyTeamDetail(TeamDetail teamDetail) throws ConstraintViolationException {
        Set<ConstraintViolation<TeamDetail>> violations = validator.validate(teamDetail);

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }

    public List<TeamDetail> getByCriteria(Optional<String> teamName, Optional<String> tournamentName, Optional<String> coachName) throws ResourceNotFoundException {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<TeamDetailEntity> cq = cb.createQuery(TeamDetailEntity.class);

        Root<TeamDetailEntity> teamDetailEntityRoot = cq.from(TeamDetailEntity.class);

        cq.select(teamDetailEntityRoot);

        List<Predicate> predicates = new ArrayList<>();

        teamName.ifPresent(s -> predicates.add(cb.like(teamDetailEntityRoot.get("team").get("teamName"), "%" + s.trim() + "%")));

        tournamentName.ifPresent(s -> predicates.add(cb.like(teamDetailEntityRoot.get("tournament").get("tournamentName"), "%" + s.trim() + "%")));

        coachName.ifPresent(s -> predicates.add(cb.like(teamDetailEntityRoot.get("coach").get("ingameName"), "%" + s.trim() + "%")));

        cq.where(predicates.toArray(new Predicate[0]));

        if (em.createQuery(cq).getResultList().isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND, ErrorMessage.KEY_RESOURCE_NOT_FOUND);
        }

        return teamDetailMapper.toDTOList(em.createQuery(cq).getResultList());
    }

}
