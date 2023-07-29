package com.project.plan.domain;

import com.project.plan.dto.CategoryReqDto;
import com.project.plan.domain.plan.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.catalina.User;
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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Plan> plans = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDelete;

    @PrePersist
    public void preSave(){
        this.isDelete = this.isDelete == null ? Boolean.FALSE : this.isDelete;
    }

    // 연관 관계 메서드
    public void setProject(Project project){
        this.project = project;
        project.getCategories().add(this);
    }

    public void setCreatedUser(Member member){
        this.createdUser = member;
        member.getCategoryCreatedUser().add(this);
    }

    public void setUpdatedUser(Member member){
        this.updatedUser = member;
        member.getCategoryUpdatedUser().add(this);
    }

    // createdUser FK인 Member 가 지워지면 null 처리
    public void removeAtCreatedUser(){
        this.createdUser = null;
    }

    public void removeAtUpdatedUser(){
        this.updatedUser = null;
    }

    // 연관 관계 메서드
    public void removeCategory(){
        this.createdUser.getCategoryCreatedUser().remove(this);
        this.updatedUser.getCategoryUpdatedUser().remove(this);
        this.project.getCategories().remove(this);
    }

    public void updateCategory(CategoryReqDto categoryReqDto, Member updatedUser){
        this.name = categoryReqDto.getName();
        this.detail = categoryReqDto.getDetail();
        // 1. 원래 있던 updatedUser 의 Member 의 categoryUpdatedUser 에서 지워주고
        this.updatedUser.getCategoryUpdatedUser().remove(this);
        // 2. 새로운 updatedUser 를 할당한 후 Member 의 categoryUpdatedUser 에 추가해준다.
        this.updatedUser = updatedUser;
        updatedUser.getCategoryUpdatedUser().add(this);
    }

    public static Category createCategory(CategoryReqDto categoryReqDto, Project project, Member createdUser){
        Category category = new Category();
        category.name = categoryReqDto.getName();
        category.detail = categoryReqDto.getDetail();
        category.setProject(project);
        category.setCreatedUser(createdUser);
        category.setUpdatedUser(createdUser);
        return category;
    }

}
