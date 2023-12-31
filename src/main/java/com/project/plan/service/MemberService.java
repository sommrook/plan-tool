package com.project.plan.service;

import com.project.plan.domain.Category;
import com.project.plan.domain.Member;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanComment;
import com.project.plan.domain.plan.PlanMember;
import com.project.plan.dto.MemberReqDto;
import com.project.plan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PlanRepository planRepository;
    private final PlanCommentRepository planCommentRepository;
    private final PlanMemberRepository planMemberRepository;

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
        for (Category category : categoryRepository.findByCreatedUser(member)){
            category.removeAtCreatedUser();
        }
        for (Category category : categoryRepository.findByUpdatedUser(member)){
            category.removeAtUpdatedUser();
        }
        for (Plan plan : planRepository.findByCreatedUser(member)){
            plan.removeAtCreatedUser();
        }
        for (Plan plan : planRepository.findByUpdatedUser(member)){
            plan.removeAtUpdatedUser();
        }
        for (PlanComment planComment : planCommentRepository.findByCreatedUser(member)){
            planComment.removeAtPlanCommentUser();
        }
        List<PlanMember> planMembers = planMemberRepository.findByMember(member);
        for (PlanMember planMember : planMembers){
            planMember.removePlanMember();
            planMemberRepository.delete(planMember);
        }
        memberRepository.delete(member);
    }
}
