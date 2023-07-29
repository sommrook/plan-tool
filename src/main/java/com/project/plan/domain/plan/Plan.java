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
        this.createdUser = null;
    }

    public void removeAtUpdatedUser(){
        this.updatedUser = null;
    }

    // plan 객체 삭제 시 호출
    public void removePlan(){
        // cascade 옵션 사용할 때에는 cascade 를 할 자식을 비우면 안된다.
        // PlanMember 는 Member 에서도 참조하고 있기 때문에 둘 중 하나만 cascade 옵션을 사용해야 한다.
        this.category.getPlans().remove(this);
        this.createdUser.getPlanCreatedUser().remove(this);
        this.updatedUser.getPlanUpdatedUser().remove(this);
        for (PlanMember planMember : this.planMembers){
            planMember.removePlanMember();
        }
        this.planMembers = new ArrayList<>();
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
