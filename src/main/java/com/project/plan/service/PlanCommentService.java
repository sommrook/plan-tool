package com.project.plan.service;

import com.project.plan.domain.Member;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanComment;
import com.project.plan.dto.PlanCommentReqDto;
import com.project.plan.repository.MemberRepository;
import com.project.plan.repository.PlanCommentRepository;
import com.project.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PlanCommentService {

    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;
    private final PlanCommentRepository planCommentRepository;

    public PlanComment save(PlanCommentReqDto planCommentReqDto, Long memberId){
        Plan plan = planRepository.findById(planCommentReqDto.getPlanId());
        Member member = memberRepository.findById(memberId);
        PlanComment planComment = null;
        if (planCommentReqDto.getParentId() != null){
            PlanComment parentComment = planCommentRepository.findById(planCommentReqDto.getParentId());
            planComment = PlanComment.createPlanComment(plan, parentComment, planCommentReqDto.getContent(), member);
        }
        else {
            planComment = PlanComment.createPlanComment(plan, null, planCommentReqDto.getContent(), member);
        }
        planCommentRepository.save(planComment);
        return planComment;
    }

    public void update(PlanCommentReqDto planCommentReqDto){
        PlanComment planComment = planCommentRepository.findById(planCommentReqDto.getPlanCommentId());
        planComment.updatePlanComment(planCommentReqDto.getContent());
    }

    public void delete(Long planCommentId){
        PlanComment planComment = planCommentRepository.findById(planCommentId);
        planComment.removePlanComment();
//        for (PlanComment childComment: planComment.getChild()){
//            childComment.removeChildPlanComment();
//            planCommentRepository.delete(childComment);
//        }
        planCommentRepository.delete(planComment);
    }

    @Transactional(readOnly = true)
    public List<PlanComment> findAll(Long planId){
        Plan plan = planRepository.findById(planId);
        return planCommentRepository.findAll(plan);
    }
}
