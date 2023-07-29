package com.project.plan.service;

import com.project.plan.domain.*;
import com.project.plan.domain.plan.DevelopStatus;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanMember;
import com.project.plan.domain.plan.PlanStatus;
import com.project.plan.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlanMemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired SolutionService solutionService;
    @Autowired ProjectService projectService;
    @Autowired CategoryService categoryService;
    @Autowired PlanService planService;
    @Autowired PlanMemberService planMemberService;

    private Member init_member1;
    private Member init_member2;
    private Member init_member3;

    private Solution init_solution;
    private Project init_project;
    private Category init_category;

    private Plan init_plan;

    @Before
    public void beforeSetting(){
        System.out.println("create Member");

        MemberReqDto memberReqDto1 = new MemberReqDto("user1", "1234", "chloe", "sommrook@gmail.com", "qa", Permission.ADMIN);
        MemberReqDto memberReqDto2 = new MemberReqDto("user2", "1243", "chloe2", "aaa@ssss.com", "qa", Permission.PLANNER);
        MemberReqDto memberReqDto3 = new MemberReqDto("user3", "12345", "chh", "aaa@gd.com", "qa", Permission.DEVELOPMENT);
        init_member1 = memberService.saveMember(memberReqDto1);
        init_member2 = memberService.saveMember(memberReqDto2);
        init_member3 = memberService.saveMember(memberReqDto3);

        SolutionReqDto solutionReqDto1 = new SolutionReqDto("Solution1", "solution1");
        init_solution = solutionService.save(solutionReqDto1);

        ProjectReqDto projectReqDto1 = new ProjectReqDto("Project1", "Project Test", init_solution.getId());
        init_project = projectService.save(projectReqDto1);

        CategoryReqDto categoryReqDto = new CategoryReqDto("category1", "category1의 내용입니다.", init_project.getId());
        init_category = categoryService.save(categoryReqDto, init_member1.getId());

        List<Long> memberIds = new ArrayList<>();

        memberIds.add(init_member1.getId());

        PlanReqDto planReqDto = new PlanReqDto("title1", "기획 변경", PlanStatus.NEW, DevelopStatus.PENDING, init_category.getId(), memberIds);
        init_plan = planService.save(planReqDto, init_member1.getId());
    }

    @Test
    public void add() {
        List<Long> workers = new ArrayList<>();
        workers.add(init_member2.getId());
        workers.add(init_member3.getId());

        PlanMemberReqDto planMemberReqDto = new PlanMemberReqDto(workers);
        planMemberService.add(init_plan.getId(), planMemberReqDto);

        List<PlanMember> findAllPlanMember = planMemberService.findAll(init_plan.getId());

        assertEquals("모든 멤버의 수는 3명이여야 한다.", 3, findAllPlanMember.size());
        assertEquals("plan의 member 수는 3명이여야 한다.", 3, init_plan.getPlanMembers().size());
    }

    @Test(expected = IllegalStateException.class)
    public void addException() {
        List<Long> workers = new ArrayList<>();
        workers.add(init_member1.getId());
        workers.add(init_member2.getId());
        workers.add(init_member3.getId());
        PlanMemberReqDto planMemberReqDto = new PlanMemberReqDto(workers);

        planMemberService.add(init_plan.getId(), planMemberReqDto);

        fail("중복 멤버 예외가 터져야 한다.");
    }

    @Test
    public void remove() {
        List<Long> workers = new ArrayList<>();
        workers.add(init_member1.getId());
        PlanMemberReqDto planMemberReqDto = new PlanMemberReqDto(workers);

        planMemberService.remove(init_plan.getId(), planMemberReqDto);

        List<PlanMember> findAllPlanMember = planMemberService.findAll(init_plan.getId());

        assertEquals("모든 멤버의 수는 0명이여야 한다.", 0, findAllPlanMember.size());
        assertEquals("plan 의 member 수는 0명이여야 한다.", 0, init_plan.getPlanMembers().size());
    }
}