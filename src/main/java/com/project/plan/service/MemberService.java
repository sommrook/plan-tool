package com.project.plan.service;

import com.project.plan.domain.Member;
import com.project.plan.dto.MemberReqDto;
import com.project.plan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(MemberReqDto memberReqDto){
        checkDuplicateAccount(memberReqDto.getAccount());
        Member member = Member.createMember(memberReqDto);
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public Member updateMember(Long memberId, MemberReqDto memberReqDto){
        Member member = memberRepository.findById(memberId);
        member.updateMember(memberReqDto);
        return member;
    }

    public void checkDuplicateName(String userName){
        List<Member> findMembers = memberRepository.findByName(userName);
        if (!findMembers.isEmpty()){
            System.out.println("findMembers = " + findMembers);
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public void checkDuplicateAccount(String account){
        List<Member> findMembers = memberRepository.findByAccount(account);
        if (!findMembers.isEmpty()){
            System.out.println("findMembers = " + findMembers);
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId);
    }

    public void deleteMember(Long memberId){
        Member member = memberRepository.findById(memberId);
        memberRepository.delete(member);
    }
}
