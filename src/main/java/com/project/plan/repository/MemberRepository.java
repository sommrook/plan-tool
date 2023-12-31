package com.project.plan.repository;

import com.project.plan.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findById(Long memberId){
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findByAccount(String account){
        return em.createQuery("select m from Member m where m.account = :account", Member.class)
                .setParameter("account", account)
                .getResultList();
    }

    public List<Member> findByIds(List<Long> memberIds){
        return em.createQuery("select m from Member m where m.id in (:ids)", Member.class)
                .setParameter("ids", memberIds)
                .getResultList();
    }

    public void delete(Member member){
        em.remove(member);
    }
}
