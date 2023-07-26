package com.project.plan.repository;

import com.project.plan.domain.Category;
import com.project.plan.domain.Member;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanComment;
import com.project.plan.domain.plan.PlanMember;
import com.project.plan.domain.plan.PlanMemberPk;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanRepository {

    private final EntityManager em;

    public void save(Plan plan){
        em.persist(plan);
    }

    public void savePlanMember(PlanMember planMember){
        em.persist(planMember);
    }

    public void savePlanComment(PlanComment planComment){
        em.persist(planComment);
    }

    public Plan findPlanById(Long planId){
        return em.find(Plan.class, planId);
    }

    public PlanMember findPlanMemberById(Plan plan, Member member){
        PlanMemberPk planMemberId = new PlanMemberPk(plan, member);
        return em.find(PlanMember.class, planMemberId);
    }

    public PlanComment findPlanCommentById(Long commentId){
        return em.find(PlanComment.class, commentId);
    }

    public List<Plan> findPlanByName(Category category, String planName){
        return em.createQuery("select p from Plan p where p.category = :category and p.name = :name", Plan.class)
                .setParameter("category", category)
                .setParameter("name", planName)
                .getResultList();
    }


    public void deletePlan(Long planId){
        em.createQuery("delete from Plan p where p.id = :id")
                .setParameter("id", planId)
                .executeUpdate();
        em.clear();
    }

    public void deletePlanMember(Plan plan, Member member){
        em.createQuery("delete from PlanMember pm where pm.plan = :plan and pm.member = :member")
                .setParameter("plan", plan)
                .setParameter("member", member)
                .executeUpdate();
        em.clear();
    }

    public void deletePlanComment(Long commentId){
        em.createQuery("delete from PlanComment pc where pc.id = :id")
                .setParameter("id", commentId)
                .executeUpdate();
        em.clear();
    }
}
