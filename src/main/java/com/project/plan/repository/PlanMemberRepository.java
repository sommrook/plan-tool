package com.project.plan.repository;

import com.project.plan.domain.Member;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanMember;
import com.project.plan.domain.plan.PlanMemberPk;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanMemberRepository {

    @Autowired
    private final EntityManager em;

    public void save(PlanMember planMember){
        em.persist(planMember);
    }

    public PlanMember findById(Plan plan, Member member){
        PlanMemberPk planMemberId = new PlanMemberPk(plan, member);
        return em.find(PlanMember.class, planMemberId);
    }

    public List<PlanMember> findAll(Plan plan){
        return em.createQuery("select pm from PlanMember pm where pm.plan = :plan", PlanMember.class)
                .setParameter("plan", plan)
                .getResultList();
    }

    public List<PlanMember> findByIds(Plan plan, List<Member> members){
        return em.createQuery("select pm from PlanMember pm where pm.plan = :plan and pm.member in (:members)", PlanMember.class)
                .setParameter("plan", plan)
                .setParameter("members", members)
                .getResultList();
    }

    public List<PlanMember> findByMember(Member member){
        return em.createQuery("select pm from PlanMember pm where pm.member = :member", PlanMember.class)
                .setParameter("member", member)
                .getResultList();
    }

    public void delete(PlanMember planMember){
        em.remove(planMember);
    }

}
