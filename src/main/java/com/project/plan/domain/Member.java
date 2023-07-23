package com.project.plan.domain;

import com.project.plan.domain.dto.MemberCreateReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
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

    private String account;

    private String password;

    @Column(name = "member_name")
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

    public static Member createMember(MemberCreateReqDto memberCreateReqDto){
        /*
        * 1. 비밀번호 암호화 => spring security
        * 2. createdDate, updatedDate 자동 생성 =>
        * */
        Member member = new Member();
        member.account = memberCreateReqDto.getAccount();
        member.password = memberCreateReqDto.getPassword();
        member.name = memberCreateReqDto.getName();
        member.email = memberCreateReqDto.getEmail();
        member.team = memberCreateReqDto.getTeam();
        member.permission = memberCreateReqDto.getPermission();
        return member;
    }

}
