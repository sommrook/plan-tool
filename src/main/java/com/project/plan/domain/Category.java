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

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
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
    }

    public void setUpdatedUser(Member member){
        this.updatedUser = member;
    }

    // createdUser FK인 Member 가 지워지면 null 처리
    public void removeAtCreatedUser(){
        // Category 객체인 나 자신에서 참조하고 있는 createdUser Member 객체 정보가 지워질 때 호출하는 메서드
        this.createdUser = null;
    }

    public void removeAtUpdatedUser(){
        // Category 객체인 나 자신에서 참조하고 있는 updatedUser Member 객체 정보가 지워질 때 호출하는 메서드
        this.updatedUser = null;
    }

    // 연관 관계 메서드
    public void removeCategory(){
        // XXX
        // Category 객체에서 나 자신을 지울 때 호출하는 메서드 => 안쓰임
        this.project.getCategories().remove(this);
    }

    public void updateCategory(CategoryReqDto categoryReqDto, Member updatedUser){
        this.name = categoryReqDto.getName();
        this.detail = categoryReqDto.getDetail();
        // 새로운 updatedUser 를 할당
        this.updatedUser = updatedUser;
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
