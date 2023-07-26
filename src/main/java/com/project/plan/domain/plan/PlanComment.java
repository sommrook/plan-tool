package com.project.plan.domain.plan;

import com.project.plan.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class PlanComment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user")
    private Member createdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private PlanComment parent;

    @OneToMany(mappedBy = "parent")
    private List<PlanComment> child = new ArrayList<>();

    public void addChildComment(PlanComment planComment){
        planComment.parent = this;
        this.getChild().add(planComment);
    }

    public void setPlan(Plan plan){
        this.plan = plan;
        plan.getPlanComments().add(this);
    }

    public static PlanComment createUpperPlanComment(Plan plan, String content){
        PlanComment planComment = new PlanComment();
        planComment.content = content;
        planComment.setPlan(plan);
        return planComment;
    }

    public static PlanComment createLowerPlanComment(Plan plan, PlanComment parentComment, String content){
        PlanComment planComment = new PlanComment();
        planComment.content = content;
        planComment.setPlan(plan);
        parentComment.addChildComment(planComment);
        return planComment;
    }
}
