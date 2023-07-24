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

    public void delete(Long memberId){
        // createQuery 의 두번째 매개변수는 result class 이다. => delete 는 result 가 없으므로 명시하지 않는다.
        em.createQuery("delete from Member m where m.id = :id")
                .setParameter("id", memberId)
                .executeUpdate();
        em.clear();
    }
}
