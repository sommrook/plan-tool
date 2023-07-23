package com.project.plan.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    @JoinColumn(name = "tool_id")
    private Tool tool;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "project")
    private List<Category> categories = new ArrayList<>();

    private Boolean isDelete;
}
