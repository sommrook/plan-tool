package com.project.plan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlanCommentReqDto {

    private String content;

    private Long planId;

    private Long parentId;
}
