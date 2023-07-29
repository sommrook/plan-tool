package com.project.plan.domain.plan;

import com.project.plan.domain.Member;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanMemberPk implements Serializable {
    private Plan plan;
    private Member member;
}
