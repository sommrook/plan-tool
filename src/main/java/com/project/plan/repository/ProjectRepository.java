package com.project.plan.repository;

import com.project.plan.domain.Project;
import com.project.plan.domain.Solution;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public void save(Project project){
        em.persist(project);
    }

    public Project findById(Long projectId){
        return em.find(Project.class, projectId);
    }

    public List<Project> findByName(Solution solution, String projectName){
        return em.createQuery("select p from Project p where p.solution = :solution and p.name = :name", Project.class)
                .setParameter("solution", solution)
                .setParameter("name", projectName)
                .getResultList();
    }

    public List<Project> findAll(Solution solution){
        return em.createQuery("select p from Project p where p.solution = :solution", Project.class)
                .setParameter("solution", solution)
                .getResultList();
    }

    public void delete(Project project){
        em.remove(project);
//        em.clear();
    }
}
