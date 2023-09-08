package com.lmluat.league.config;

import com.lmluat.league.entity.RoleEnum;
import com.lmluat.league.service.model.UserDTO;
import lombok.AllArgsConstructor;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@AllArgsConstructor
public class LeagueSecurityContext implements SecurityContext {

    private String name;
    private RoleEnum role;

    @Override
    public Principal getUserPrincipal() {
        return () -> name;
    }

    @Override
    public boolean isUserInRole(String s) {
        return role.name().equals(s);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
