package com.project.plan.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String account;

    private String password;

    @Column(name = "member_name")
    private String name;

    private String email;

    @Column(name = "belong_team")
    private String team;

    @Enumerated(EnumType.STRING)
    private Permission permission;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
