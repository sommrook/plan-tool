package com.project.plan.domain.plan;

import com.project.plan.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Entity
@Getter
@IdClass(PlanMemberPk.class)
public class PlanMember {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private DevelopStatus developStatus;

    public static void createPlanMember(Plan plan, Member member){
        PlanMember planMember = new PlanMember();
        planMember.plan = plan;
        planMember.member = member;
        plan.getPlanMembers().add(planMember);
    }
}
