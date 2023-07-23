package com.project.plan.domain.dto;

import com.project.plan.domain.Permission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateReqDto {
    private String account;
    private String password;
    private String name;
    private String email;
    private String team;
    private Permission permission;
}
