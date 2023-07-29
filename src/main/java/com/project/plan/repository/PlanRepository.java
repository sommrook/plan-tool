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

    public Plan findById(Long planId){
        return em.find(Plan.class, planId);
    }


    public List<Plan> findByTitle(Category category, String title){
        return em.createQuery("select p from Plan p where p.category = :category and p.title = :title", Plan.class)
                .setParameter("category", category)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Plan> findAll(Category category){
        return em.createQuery("select p from Plan p where p.category = :category", Plan.class)
                .setParameter("category", category)
                .getResultList();
    }

    public void delete(Plan plan){
        em.remove(plan);
//        em.clear();
    }

}
