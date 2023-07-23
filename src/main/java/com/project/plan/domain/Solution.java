package com.project.plan.domain;

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

    @Column(name = "solution_name")
    private String name;

    @Column(name = "solution_detail")
    private String detail;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Boolean isDelete;

    @OneToMany(mappedBy = "solution")
    private List<Tool> tools = new ArrayList<>();
}
