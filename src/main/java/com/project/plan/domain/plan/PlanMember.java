package com.project.plan.domain.plan;

import com.project.plan.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
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
    @Column(nullable = false, columnDefinition = "varchar(12) default 'PENDING'")
    private DevelopStatus developStatus;

    @PrePersist
    public void setDevelopStatus(){
        this.developStatus = this.developStatus == null ? DevelopStatus.PENDING : this.developStatus;
    }

    // 유저가 develop status 를 변화할 시
    public void setDevelopStatus(DevelopStatus developStatus){
        this.developStatus = developStatus;
    }

    public void setPlan(Plan plan){
        this.plan = plan;
        plan.getPlanMembers().add(this);
    }

    public void setMember(Member member){
        this.member = member;
        member.getPlanMemberUser().add(this);
    }

    public void removePlanMember(){
        this.plan.getPlanMembers().remove(this);
        this.member.getPlanMemberUser().remove(this);
    }

    public static PlanMember createPlanMember(Plan plan, Member member){
        PlanMember planMember = new PlanMember();
        planMember.setMember(member);
        planMember.setPlan(plan);
        plan.getPlanMembers().add(planMember);
        return planMember;
    }
}
