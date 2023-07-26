package com.project.plan.dto;

import com.project.plan.domain.Permission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberReqDto {
    private String account;
    private String password;
    private String name;
    private String email;
    private String team;
    private Permission permission;

}
