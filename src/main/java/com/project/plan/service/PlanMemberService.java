package com.project.plan.service;

import com.project.plan.domain.Member;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanMember;
import com.project.plan.dto.PlanMemberReqDto;
import com.project.plan.repository.MemberRepository;
import com.project.plan.repository.PlanMemberRepository;
import com.project.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanMemberService {
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;
    private final PlanMemberRepository planMemberRepository;

    @Transactional
    public void add(Long planId, PlanMemberReqDto planMemberReqDto){
        List<Member> members = memberRepository.findByIds(planMemberReqDto.getWorkers());
        Plan plan = planRepository.findById(planId);
        duplicateCheck(plan, members);
        for (Member member : members){
            PlanMember planMember = PlanMember.createPlanMember(plan, member);
            planMemberRepository.save(planMember);
        }
    }

    @Transactional
    public void remove(Long planId, PlanMemberReqDto planMemberReqDto){
        Plan plan = planRepository.findById(planId);
        List<Member> members = memberRepository.findByIds(planMemberReqDto.getWorkers());
        for (Member member : members){
            PlanMember planMember = planMemberRepository.findById(plan, member);
            planMember.removePlanMember();
            planMemberRepository.delete(planMember);
        }
    }

    public List<PlanMember> findAll(Long planId){
        Plan plan = planRepository.findById(planId);
        return planMemberRepository.findAll(plan);
    }

    public PlanMember findOne(Long planId, Long memberId){
        Plan plan = planRepository.findById(planId);
        Member member = memberRepository.findById(memberId);
        return planMemberRepository.findById(plan, member);
    }

    private void duplicateCheck(Plan plan, List<Member> members){
        List<PlanMember> planMembers = planMemberRepository.findByIds(plan, members);
        if (!planMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 멤버입니다.");
        }
    }
}
