package com.lmluat.league.config;

import com.lmluat.league.entity.RoleEnum;
import com.lmluat.league.exception.AuthorizationException;
import com.lmluat.league.exception.ResponseBody;
import com.lmluat.league.utils.JwtUtils;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo info;

    @Inject
    private JwtUtils jwtUtils;

    public AuthorizationFilter() {
    }

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        //@DenyAll in class or method: Abort Always
        if (isDenied()) {
            request.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity(new ResponseBody("Forbidden access", "KEY_FORBIDDEN_ACCESS", Response.Status.FORBIDDEN))
                    .build());
            return;
        }
        RolesAllowed methodRoles = info.getResourceMethod().getAnnotation(RolesAllowed.class);
        RolesAllowed classRoles = info.getResourceClass().getAnnotation(RolesAllowed.class);

        if (methodRoles == null && classRoles == null) {

            return;
        }

        String authHeader = request.getHeaderString("Authorization");
        System.out.println(authHeader);
        if (isNotValidJwt(authHeader)) {
            request.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ResponseBody("KEY_UNAUTHORIZED_ACCESS", "UNAUTHORIZED_ACCESS", Response.Status.UNAUTHORIZED)).build());
            return;
        }

        RoleEnum role = jwtUtils.getRoleFromToken(authHeader);
        System.out.println(role);
        String name = jwtUtils.getNameFromToken(authHeader);
        SecurityContext sc = new LeagueSecurityContext(name,role);

        request.setSecurityContext(sc);

        if (hasNoRole(classRoles, request.getSecurityContext())
                || hasNoRole(methodRoles, request.getSecurityContext())) {
            request.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity(new ResponseBody("Unauthorized access","UNAUTHORIZED_ACCESS",Response.Status.FORBIDDEN)).build());
        }
    }

    private boolean isDenied() {
        DenyAll methodDenyAll = info.getResourceMethod().getAnnotation(DenyAll.class);
        DenyAll classDenyAll = info.getResourceClass().getAnnotation(DenyAll.class);
        return methodDenyAll != null || classDenyAll != null;
    }

    private boolean isNotValidJwt(String header) {
        if (header == null) {
            return true;
        }

        try {
            jwtUtils.validateJwtToken(header);
        } catch (AuthorizationException e) {
            return true;
        }

        return !header.startsWith("Bearer ");
    }

    private boolean hasNoRole(RolesAllowed anno, SecurityContext sc) {
        if (anno == null) {
            return false;
        }

        String[] roleStrings = anno.value();
        for (String roleString : roleStrings) {
            if (sc.isUserInRole(roleString)) {
                return false;
            }
        }
        return true;
    }
}
