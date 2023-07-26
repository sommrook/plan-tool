package com.project.plan.repository;

import com.project.plan.domain.Category;
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

    public void delete(Long categoryId){
        em.createQuery("delete from Category c where c.id = :ic")
                .setParameter("id", categoryId)
                .executeUpdate();

        em.clear();
    }
}
