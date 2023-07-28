package com.project.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PlanMemberReqDto {

    private List<Long> workers;

}
