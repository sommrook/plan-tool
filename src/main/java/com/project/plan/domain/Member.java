package com.project.plan.domain;

import com.project.plan.dto.MemberReqDto;
import jakarta.persistence.*;
import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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

}
