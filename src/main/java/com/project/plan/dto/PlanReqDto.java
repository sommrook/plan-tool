package com.project.plan.dto;

import com.project.plan.domain.plan.DevelopStatus;
import com.project.plan.domain.plan.PlanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PlanReqDto {
    private String title;

    private String detail;

    private PlanStatus planStatus;

    private DevelopStatus developStatus;

    private Long categoryId;

    private List<Long> workers;
}
