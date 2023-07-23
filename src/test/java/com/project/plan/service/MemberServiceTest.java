package com.project.plan.service;

import com.project.plan.domain.Member;
import com.project.plan.domain.Permission;
import com.project.plan.domain.dto.MemberCreateReqDto;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @DisplayName("회원가입")
    @Commit
    @Test
    public void saveMember() throws Exception {
        // given
        MemberCreateReqDto memberCreateReqDto = new MemberCreateReqDto();
        memberCreateReqDto.setAccount("user1");
        memberCreateReqDto.setName("chloe");
        memberCreateReqDto.setPassword("1234");
        memberCreateReqDto.setPermission(Permission.ADMIN);

        // when
        Long savedId = memberService.saveMember(memberCreateReqDto);

    }

    @Test(expected = IllegalStateException.class)
    @DisplayName("중복 회원 가입")
    public void saveMemberThrows() throws Exception {
        //given

        //when

        //then
    }
}