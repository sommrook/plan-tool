package com.project.plan.domain.plan;

import com.project.plan.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class PlanMemberPk implements Serializable {
    private Plan plan;
    private Member member;
}
