package com.project.plan.dto;

import com.project.plan.domain.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberReqDto {
    private String account;
    private String password;
    private String name;
    private String email;
    private String team;
    private Permission permission;

}
