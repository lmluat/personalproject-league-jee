package com.lmluat.league.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lmluat.league.entity.RoleEnum;
import com.lmluat.league.entity.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;

    private String name;

    private String password;

    private StatusEnum status;

    private RoleEnum role;
}
