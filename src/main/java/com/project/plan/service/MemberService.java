package com.project.plan.service;

import com.project.plan.domain.Member;
import com.project.plan.domain.dto.MemberCreateReqDto;
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
    public Long saveMember(MemberCreateReqDto memberCreateReqDto){
        Member member = Member.createMember(memberCreateReqDto);
        memberRepository.save(member);
        return member.getId();
    }

    public void checkDuplicateMember(String userName){
        List<Member> findMembers = memberRepository.findByName(userName);
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
        memberRepository.delete(memberId);
    }
}
