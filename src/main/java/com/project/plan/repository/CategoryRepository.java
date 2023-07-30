package com.project.plan.repository;

import com.project.plan.domain.Category;
import com.project.plan.domain.Member;
import com.project.plan.domain.Project;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category){
        em.persist(category);
    }

    public Category findById(Long categoryId){
        return em.find(Category.class, categoryId);
    }

    public List<Category> findByName(Project project, String categoryName){
        return em.createQuery("select c from Category c where c.project = :project and c.name = :name", Category.class)
                .setParameter("project", project)
                .setParameter("name", categoryName)
                .getResultList();
    }

    public List<Category> findAll(Project project){
        return em.createQuery("select c from Category c where c.project = :project", Category.class)
                .setParameter("project", project)
                .getResultList();
    }

    public List<Category> findByCreatedUser(Member member){
        return em.createQuery("select c from Category c where c.createdUser = :member", Category.class)
                .setParameter("member", member)
                .getResultList();
    }

    public List<Category> findByUpdatedUser(Member member){
        return em.createQuery("select c from Category c where c.updatedUser = :member", Category.class)
                .setParameter("member", member)
                .getResultList();
    }

    public void delete(Category category){
        em.remove(category);
    }
}
