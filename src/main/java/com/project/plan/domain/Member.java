package com.project.plan.domain;

import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanComment;
import com.project.plan.domain.plan.PlanMember;
import com.project.plan.dto.MemberReqDto;
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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String account;

    //추후에 spring security 적용
    @Column(nullable = false)
    private String password;

    @Column(name = "member_name", nullable = false)
    private String name;

    private String email;

    @Column(name = "belong_team")
    private String team;

    @Enumerated(EnumType.STRING)
    private Permission permission;

//    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "createdUser")
    private List<Category> categoryCreatedUser = new ArrayList<>();

    @OneToMany(mappedBy = "updatedUser")
    private List<Category> categoryUpdatedUser = new ArrayList<>();

    // set null
    @OneToMany(mappedBy = "createdUser")
    private List<Plan> planCreatedUser = new ArrayList<>();

    // set null
    @OneToMany(mappedBy = "updatedUser")
    private List<Plan> planUpdatedUser = new ArrayList<>();


    @OneToMany(mappedBy = "createdUser")
    private List<PlanComment> planCommentUser = new ArrayList<>();

    // cascade
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PlanMember> memberPlans = new ArrayList<>();

    public static Member createMember(MemberReqDto memberReqDto){
        /*
        * 1. 비밀번호 암호화 => spring security
        * 2. createdDate, updatedDate 자동 생성 =>
        * */
        Member member = new Member();
        member.account = memberReqDto.getAccount();
        member.password = memberReqDto.getPassword();
        member.name = memberReqDto.getName();
        member.email = memberReqDto.getEmail();
        member.team = memberReqDto.getTeam();
        member.permission = memberReqDto.getPermission();
        return member;
    }

    public void updateMember(MemberReqDto memberReqDto){
        this.password = memberReqDto.getPassword();
        this.name = memberReqDto.getName();
        this.email = memberReqDto.getEmail();
        this.team = memberReqDto.getTeam();
        this.permission = memberReqDto.getPermission();
    }

    public void removeUser(){
        for (Category category: this.categoryCreatedUser){
            category.removeCreatedUser();
        }
        for (Category category: this.categoryUpdatedUser){
            category.removeUpdatedUser();
        }
        for (Plan plan: this.planCreatedUser){
            plan.removeCreatedUser();
        }
        for (Plan plan: this.planUpdatedUser){
            plan.removeUpdatedUser();
        }
        for (PlanComment planComment: this.planCommentUser){
            planComment.removePlanCommentUser();
        }
    }

}
