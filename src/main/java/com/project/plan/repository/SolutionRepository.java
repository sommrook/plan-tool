package com.project.plan.repository;
import com.project.plan.domain.Solution;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SolutionRepository {

    private final EntityManager em;

    public void save(Solution solution){
        em.persist(solution);
    }

    public Solution findById(Long solutionId){
        return em.find(Solution.class, solutionId);
    }

    public List<Solution> findAll(){
        return em.createQuery("select s from Solution s", Solution.class)
                .getResultList();
    }

    public List<Solution> findByName(String solutionName){
        return em.createQuery("select s from Solution s where name = :name", Solution.class)
                .setParameter("name", solutionName)
                .getResultList();
    }

    public void delete(Solution solution){
        em.remove(solution);
    }

}
