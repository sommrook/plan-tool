package com.project.plan.domain;

import com.project.plan.domain.dto.CategoryReqDto;
import com.project.plan.domain.plan.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_detail")
    private String detail;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user")
    private Member createdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_user")
    private Member updatedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pjt_id")
    private Project project;

    @OneToMany(mappedBy = "category")
    private List<Plan> plans = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDelete;

    // 연관 관계 메서드
    public void setProject(Project project){
        this.project = project;
        project.getCategories().add(this);
    }

    public static Category createCategory(Member createdUser, Member updatedUser, Project project, CategoryReqDto categoryReqDto){
        Category category = new Category();
        category.name = categoryReqDto.getName();
        category.detail = categoryReqDto.getDetail();
        category.createdUser = createdUser;
        category.updatedUser = updatedUser;
        category.setProject(project);
        return category;
    }
}
