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

    public void removeAtPlanCommentUser(){
        // PlanComment 객체가 참조하고 있는 createdUser 인 Member 객체가 삭제될 때 호출되는 메서드
        this.createdUser = null;
    }

    // planComment 객체 삭제 시 호출
    public void removePlanComment() {
        // 부모인 Plan 에서 삭제될 때
        this.plan.getPlanComments().remove(this);
        this.createdUser.getPlanCommentUser().remove(this);
//        if (this.parent != null) {
//            this.parent.getChild().remove(this);
//         }
    }

    public void removeChildPlanComment() {
        this.plan.getPlanComments().remove(this);
        this.createdUser.getPlanCommentUser().remove(this);
        /**
         * 어차피 parent가 지워지는 주체이니까 굳이 밑에 구문을 쓰지 않아도 된다.
         * if (this.parent != null) {
         *     this.parent.getChild().remove(this);
         * }
         **/
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
