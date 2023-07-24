package com.project.plan.domain;

import com.project.plan.domain.dto.SolutionRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Solution {

    @Id
    @GeneratedValue
    @Column(name = "solution_id")
    private Long id;

    @Column(name = "solution_name", nullable = false, unique = true)
    private String name;

    @Column(name = "solution_detail")
    private String detail;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDelete;

    @OneToMany(mappedBy = "solution")
    private List<Tool> tools = new ArrayList<>();

    @PrePersist
    protected void setCreatedDate(){
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void setUpdatedDate(){
        this.updatedDate = LocalDateTime.now();
    }

    public static Solution createSolution(SolutionRequestDto solutionRequestDto){
        Solution solution = new Solution();
        solution.name = solutionRequestDto.getName();
        solution.detail = solutionRequestDto.getDetail();
        return solution;
    }

    public void updateSolution(SolutionRequestDto solutionRequestDto){
        this.name = solutionRequestDto.getName();
        this.detail = solutionRequestDto.getDetail();
    }
}
