package com.project.plan.domain;

import com.project.plan.dto.ProjectReqDto;
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
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "pjt_id")
    private Long id;

    @Column(name = "pjt_name")
    private String name;

    @Column(name = "pjt_detail")
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id")
    private Solution solution;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "project")
    private List<Category> categories = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDelete;

    @PrePersist
    public void preSave(){
        this.isDelete = this.isDelete == null ? Boolean.FALSE : this.isDelete;
    }

    // 연관 관계 메서드
    public void setSolution(Solution solution){
        this.solution = solution;
        solution.getProjects().add(this);
    }

    public static Project createProject(Solution solution, ProjectReqDto projectReqDto){
        Project project = new Project();
        project.name = projectReqDto.getName();
        project.detail = projectReqDto.getDetail();
        project.setSolution(solution);
        return project;
    }

    public void updateProject(ProjectReqDto projectReqDto){
        this.name = projectReqDto.getName();
        this.detail = projectReqDto.getDetail();
    }
}
