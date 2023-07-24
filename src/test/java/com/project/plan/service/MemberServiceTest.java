package com.project.plan.service;

import com.project.plan.domain.Member;
import com.project.plan.domain.Permission;
import com.project.plan.domain.dto.MemberRequestDto;
import com.project.plan.repository.MemberRepository;
import org.hibernate.query.IllegalQueryOperationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    @Before
    public void createMember() throws Exception{
        System.out.println("create Member");

        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setAccount("user1");
        memberRequestDto.setName("chloe");
        memberRequestDto.setPassword("1234");
        memberRequestDto.setPermission(Permission.ADMIN);

        memberService.saveMember(memberRequestDto);
    }

    @DisplayName("회원가입")
    @Test
    public void saveMember() throws Exception {
        // given
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setAccount("user2");
        memberRequestDto.setName("chloe");
        memberRequestDto.setPassword("1234");
        memberRequestDto.setPermission(Permission.ADMIN);

        // when
        memberService.saveMember(memberRequestDto);
        List<Member> members = memberService.findAll();

        for(Member member: members){
            System.out.println("member account = " + member.getAccount());
        }

        // then
        assertEquals("회원의 수는 2명이여야 한다.", 2, memberService.findAll().size());

    }

    @Test(expected = IllegalStateException.class)
    @DisplayName("중복 회원 가입")
    public void saveMemberThrows() throws Exception {
        //given
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setAccount("user1");
        memberRequestDto.setName("chloeeee");
        memberRequestDto.setPassword("1234");
        memberRequestDto.setPermission(Permission.ADMIN);

        //when
        memberService.saveMember(memberRequestDto);

        //then
        Assert.fail("예외가 발생해야 한다.");
    }

    @Test
    @DisplayName("회원 정보 수정")
    public void updateMember() throws Exception{
        // given
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setAccount("user2");
        memberRequestDto.setName("chloe");
        memberRequestDto.setPassword("1234");
        memberRequestDto.setPermission(Permission.ADMIN);
        Member member = memberService.saveMember(memberRequestDto);

        assertEquals("회원 이름", member.getName(), "chloe");
        assertEquals("비밀 번호", member.getPassword(), "1234");
        assertEquals("회원 권한", member.getPermission(), Permission.ADMIN);

        // when
        MemberRequestDto memberUpdateReqDto = new MemberRequestDto();
        memberUpdateReqDto.setName("chloe update");
        memberUpdateReqDto.setPassword("333");
        memberUpdateReqDto.setPermission(Permission.PLANNER);
        Member updatedMember = memberService.updateMember(member.getId(), memberUpdateReqDto);

        // then
        assertEquals("회원의 이름이 달라져야 한다.", member.getName(), "chloe update");
        assertEquals("비밀 번호가 달라져야 한다.", member.getPassword(), "333");
        assertEquals("회원 권한이 달라져야 한다.", member.getPermission(), Permission.PLANNER);
        System.out.println("member = " + member);
        System.out.println("updatedMember = " + updatedMember);
        assertEquals("가리키는 멤버 객체가 같은가?", member, updatedMember);

    }

    @Test()
    @DisplayName("회원 삭제")
    public void deleteMember(){
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setAccount("user2");
        memberRequestDto.setName("chloe");
        memberRequestDto.setPassword("1234");
        memberRequestDto.setPermission(Permission.ADMIN);
        Member member = memberService.saveMember(memberRequestDto);

        Long memberId = member.getId();

        assertEquals("회원 이름", member.getName(), "chloe");
        assertEquals("비밀 번호", member.getPassword(), "1234");
        assertEquals("회원 권한", member.getPermission(), Permission.ADMIN);

        memberService.deleteMember(memberId);

        assertEquals("회원 삭제 후 전체 회원 수 감소", memberService.findAll().size(), 1);

        Member findMember = memberService.findById(memberId);
        System.out.println("findMember = " + findMember);

        if (findMember == null){
            System.out.println("member 는 삭제 되었습니다.");
        } else {
            Assert.fail("member 가 제대로 삭제되지 않았습니다.");
        }
    }
}