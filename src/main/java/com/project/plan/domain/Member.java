package com.project.plan.domain;

import com.project.plan.domain.dto.MemberRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

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

    public static Member createMember(MemberRequestDto memberRequestDto){
        /*
        * 1. 비밀번호 암호화 => spring security
        * 2. createdDate, updatedDate 자동 생성 =>
        * */
        Member member = new Member();
        member.account = memberRequestDto.getAccount();
        member.password = memberRequestDto.getPassword();
        member.name = memberRequestDto.getName();
        member.email = memberRequestDto.getEmail();
        member.team = memberRequestDto.getTeam();
        member.permission = memberRequestDto.getPermission();
        return member;
    }

    public void updateMember(MemberRequestDto memberRequestDto){
        this.password = memberRequestDto.getPassword();
        this.name = memberRequestDto.getName();
        this.email = memberRequestDto.getEmail();
        this.team = memberRequestDto.getTeam();
        this.permission = memberRequestDto.getPermission();
    }

}
