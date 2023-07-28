package com.project.plan.service;

import com.project.plan.domain.*;
import com.project.plan.domain.plan.DevelopStatus;
import com.project.plan.domain.plan.Plan;
import com.project.plan.domain.plan.PlanMember;
import com.project.plan.domain.plan.PlanStatus;
import com.project.plan.dto.*;
import com.project.plan.repository.MemberRepository;
import com.project.plan.repository.ProjectRepository;
import com.project.plan.repository.SolutionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
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
public class PlanServiceTest {
    @Autowired MemberService memberService;
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
    }

    @Test
    public void save() {
        System.out.println("create Plan");
        List<Long> memberIds = new ArrayList<>();
        memberIds.add(init_member1.getId());
        memberIds.add(init_member2.getId());
        PlanReqDto planReqDto = new PlanReqDto("title1", "기획 변경", PlanStatus.NEW, DevelopStatus.PENDING, init_category.getId(), memberIds);
        Plan plan = planService.save(planReqDto, init_member1.getId());

        List<PlanMember> planMembers = planMemberService.findAll(plan.getId());
        PlanMember planMember1 = planMemberService.findOne(plan.getId(), init_member1.getId());
        PlanMember planMember2 = planMemberService.findOne(plan.getId(), init_member2.getId());

        assertEquals("plan title의 값이 일치해야 한다.", "title1", plan.getTitle());
        assertEquals("plan detail의 값이 일치해야 한다.", "기획 변경", plan.getDetail());
        assertEquals("plan의 member가 일치해야 한다.", 2, plan.getPlanMembers().size());

        assertEquals("planMember의 정보와도 일치해야 한다.", 2, planMembers.size());
        assertEquals("plan의 createdUser가 일치해야 한다.", init_member1, plan.getCreatedUser());
        Assertions.assertThat(init_member1.getPlanCreatedUser()).contains(plan);
        Assertions.assertThat(init_member1.getPlanMemberUser()).contains(planMember1);
        Assertions.assertThat(init_member2.getPlanMemberUser()).contains(planMember2);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}