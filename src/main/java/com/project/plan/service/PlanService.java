package com.project.plan.service;

import com.project.plan.domain.Category;
import com.project.plan.domain.Member;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanComment;
import com.project.plan.domain.plan.PlanMember;
import com.project.plan.dto.PlanReqDto;
import com.project.plan.repository.CategoryRepository;
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
public class PlanService {
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PlanRepository planRepository;
    private final PlanMemberRepository planMemberRepository;

    @Transactional
    public Plan save(PlanReqDto planReqDto, Long memberId){
        Member member = memberRepository.findById(memberId);
        Category category = categoryRepository.findById(planReqDto.getCategoryId());
        duplicateCheck(planReqDto.getTitle(), category);

        Plan plan = Plan.createPlan(category, member, planReqDto);
        planRepository.save(plan);

        List<Member> workers = memberRepository.findByIds(planReqDto.getWorkers());
        for (Member worker : workers){
            PlanMember planMember= PlanMember.createPlanMember(plan, worker);
            planMemberRepository.save(planMember);
        }

        return plan;
    }

    @Transactional
    public void update(Long planId, PlanReqDto planReqDto, Long memberId){
        Member updatedUser = memberRepository.findById(memberId);
        Plan plan = planRepository.findById(planId);
        duplicateCheck(planReqDto.getTitle(), plan.getCategory());
        plan.updatePlan(planReqDto, updatedUser);
    }

    public List<Plan> findAll(Long categoryId){
        Category category = categoryRepository.findById(categoryId);
        return planRepository.findAll(category);
    }

    public Plan findOne(Long planId){
        return planRepository.findById(planId);
    }

    @Transactional
    public void delete(Long planId){
        Plan plan = planRepository.findById(planId);
        List<PlanMember> planMembers = plan.getPlanMembers();
        plan.removePlan();
        for (PlanMember planMember : planMembers){
            planMemberRepository.delete(planMember);
        }
        planRepository.delete(plan);
    }

    private void duplicateCheck(String planTitle, Category category){
        List<Plan> plans = planRepository.findByTitle(category, planTitle);
        if (!plans.isEmpty()){
            throw new IllegalStateException("이미 존재하는 타이틀입니다.");
        }
    }
}
