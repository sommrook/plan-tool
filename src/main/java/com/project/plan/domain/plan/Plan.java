package com.project.plan.domain.plan;

import com.project.plan.domain.Category;
import com.project.plan.domain.Member;
import com.project.plan.dto.PlanReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Plan {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private Long id;

    @Column(name = "plan_title", unique = true)
    private String title;

    @Column(name = "plan_detail")
    private String detail;

    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    @Enumerated(EnumType.STRING)
    private DevelopStatus developStatus;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user")
    private Member createdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_user")
    private Member updatedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PlanComment> planComments = new ArrayList<>();


    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PlanMember> planMembers = new ArrayList<>();

    // 연관 관계 메서드
    public void setCategory(Category category){
        this.category = category;
        category.getPlans().add(this);
    }

    public void setCreatedUser(Member member){
        this.createdUser = member;
        member.getPlanCreatedUser().add(this);
    }

    public void setUpdatedUser(Member member){
        this.updatedUser = member;
        member.getPlanUpdatedUser().add(this);
    }

    public void removeCreatedUser(){
        this.createdUser = null;
    }

    public void removeUpdatedUser(){
        this.updatedUser = null;
    }

    // plan 객체 삭제 시 호출
    public void removePlan(){
        this.createdUser.getPlanCreatedUser().remove(this);

    }

    public static Plan createPlan(Category category, Member createdUser, List<Member> workers, PlanReqDto planReqDto){
        Plan plan = new Plan();
        plan.title = planReqDto.getTitle();
        plan.detail = planReqDto.getDetail();
        plan.planStatus = planReqDto.getPlanStatus();
        plan.developStatus = planReqDto.getDevelopStatus();
        plan.setCategory(category);
        plan.setCreatedUser(createdUser);
        plan.setUpdatedUser(createdUser);

        for (Member member: workers){
            PlanMember.createPlanMember(plan, member);
        }
        return plan;
    }

}
