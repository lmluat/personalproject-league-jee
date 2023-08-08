package com.lmluat.league.service;

import com.lmluat.league.dao.TeamDetailDAO;
import com.lmluat.league.service.mapper.TeamDetailMapper;
import com.lmluat.league.service.model.TeamDetail;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

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
    private TeamDetailMapper teamDetailMapper;

    public List<TeamDetail> getAll() {
        return teamDetailMapper.toDTOList(teamDetailDAO.findAll());
    }

}
