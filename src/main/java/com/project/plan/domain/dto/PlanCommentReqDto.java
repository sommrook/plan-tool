package com.project.plan.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlanCommentReqDto {

    private String content;

    private Long planId;

    private Long parentId;
}
