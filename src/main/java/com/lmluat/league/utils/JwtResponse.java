package com.lmluat.league.utils;

import com.lmluat.league.entity.RoleEnum;
import com.lmluat.league.entity.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String token;
    private String name;

    private RoleEnum role;

    private String type = "Bearer";
    private StatusEnum status;

    public JwtResponse(String token, String name, RoleEnum role, StatusEnum status) {
        this.token = token;
        this.name = name;
        this.role = role;
        this.status = status;
    }
}
