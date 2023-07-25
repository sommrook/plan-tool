package com.project.plan.domain.dto;

import com.project.plan.domain.plan.DevelopStatus;
import com.project.plan.domain.plan.PlanStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PlanRequestDto {

    private String title;

    private String detail;

    private PlanStatus planStatus;

    private DevelopStatus developStatus;

    private List<Long> workers;
}
