package com.project.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryReqDto {

    private String name;

    private String detail;

    private Long projectId;

}
