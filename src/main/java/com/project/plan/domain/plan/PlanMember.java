package com.project.plan.domain.plan;

import com.project.plan.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

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
    }

    public void removePlanMember(){
        // Plan에서 지울 때가 아닌 PlanMember 객체 자체적으로 지울 때 호출하는 연관관계 메서드
        this.plan.getPlanMembers().remove(this);
    }

    public static PlanMember createPlanMember(Plan plan, Member member){
        PlanMember planMember = new PlanMember();
        planMember.setMember(member);
        planMember.setPlan(plan);
        return planMember;
    }
}
