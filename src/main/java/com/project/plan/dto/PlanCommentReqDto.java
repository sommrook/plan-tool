package com.project.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PlanCommentReqDto {

    private String content;

    // 처음 생성시
    private Long planId;

    // 처음 생성시
    private Long parentId;

    // 업데이트시
    private Long planCommentId;

}
