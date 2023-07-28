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

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<PlanComment> child = new ArrayList<>();

    public void addChildComment(PlanComment planComment){
        planComment.parent = this;
        this.getChild().add(planComment);
    }

    public void setPlan(Plan plan){
        this.plan = plan;
        plan.getPlanComments().add(this);
    }

    public void setCreatedUser(Member member){
        this.createdUser = member;
        member.getPlanCommentUser().add(this);
    }

    public void removePlanCommentUser(){
        this.createdUser = null;
    }

    // planComment 객체 삭제 시 호출
    public void removePlanComment(){
        this.plan.getPlanComments().remove(this);
        this.createdUser.getPlanCommentUser().remove(this);
        if (this.parent != null){
            this.parent.getChild().remove(this);
        }
    }

    public void updatePlanComment(String content){
        this.content = content;
    }

    public static PlanComment createPlanComment(Plan plan, PlanComment parentComment, String content, Member createdUser){
        PlanComment planComment = new PlanComment();
        planComment.content = content;
        planComment.setPlan(plan);
        planComment.setCreatedUser(createdUser);
        if (parentComment != null){
            parentComment.addChildComment(planComment);
        }
        return planComment;
    }
}
