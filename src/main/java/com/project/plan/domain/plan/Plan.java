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

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private List<PlanComment> planComments = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
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

    public void removeAtCreatedUser(){
        // Plan 객체인 나 자신이 참조하고 있는 createdUser 가 삭제될 때 호출되는 메서드
        this.createdUser = null;
    }

    public void removeAtUpdatedUser(){
        // Plan 객체인 나 자신이 참조하고 있는 updatedUser 가 삭제될 때 호출되는 메서드
        this.updatedUser = null;
    }

    // plan 객체 삭제 시 호출
    public void removePlan(){
        // PlanMember 는 Member 에서도 참조하고 있기 때문에 둘 중 하나만 cascade 옵션을 사용해야 한다.
//        this.category.getPlans().remove(this);
//        this.createdUser.getPlanCreatedUser().remove(this);
//        this.updatedUser.getPlanUpdatedUser().remove(this);

        // cascade 를 사용하지 않을 때 자식의 또다른 부모 객체에서 "자식"의 정보를 지워줘야 한다.
        for (PlanMember planMember : this.planMembers){
            planMember.removePlanMemberUser();
        }
    }

    public void updatePlan(PlanReqDto planReqDto, Member updatedUser){
        this.title = planReqDto.getTitle();
        this.detail = planReqDto.getDetail();
        this.planStatus = planReqDto.getPlanStatus();
        this.developStatus = planReqDto.getDevelopStatus();

        // 기존 updatedUser 지워주고
        this.updatedUser.getPlanUpdatedUser().remove(this);
        setUpdatedUser(updatedUser);
    }

    public static Plan createPlan(Category category, Member createdUser, PlanReqDto planReqDto){
        Plan plan = new Plan();
        plan.title = planReqDto.getTitle();
        plan.detail = planReqDto.getDetail();
        plan.planStatus = planReqDto.getPlanStatus();
        plan.developStatus = planReqDto.getDevelopStatus();
        plan.setCategory(category);
        plan.setCreatedUser(createdUser);
        plan.setUpdatedUser(createdUser);
        return plan;
    }

}
