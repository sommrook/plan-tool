package com.project.plan.repository;

import com.project.plan.domain.Member;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanComment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class PlanCommentRepository {

    @Autowired
    private final EntityManager em;

    public void save(PlanComment planComment){
        em.persist(planComment);
    }

    public PlanComment findById(Long commentId){
        return em.find(PlanComment.class, commentId);
    }

    public List<PlanComment> findAll(Plan plan){
        return em.createQuery("select pc from PlanComment pc where pc.plan = :plan", PlanComment.class)
                .setParameter("plan", plan)
                .getResultList();
    }

    public List<PlanComment> findByCreatedUser(Member member){
        return em.createQuery("select pc from PlanComment pc where pc.createdUser = :member", PlanComment.class)
                .setParameter("member", member)
                .getResultList();
    }

    public void delete(PlanComment planComment){
        em.remove(planComment);
    }

}
