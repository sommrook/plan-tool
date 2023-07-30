package com.project.plan.service;

import com.project.plan.domain.*;
import com.project.plan.domain.plan.*;
import com.project.plan.dto.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlanCommentServiceTest {
    @Autowired MemberService memberService;
    @Autowired SolutionService solutionService;
    @Autowired ProjectService projectService;
    @Autowired CategoryService categoryService;
    @Autowired PlanService planService;
    @Autowired PlanCommentService planCommentService;

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
        memberIds.add(init_member2.getId());

        PlanReqDto planReqDto = new PlanReqDto("title1", "기획 변경", PlanStatus.NEW, DevelopStatus.PENDING, init_category.getId(), memberIds);
        init_plan = planService.save(planReqDto, init_member1.getId());
    }

    @Test
    public void save() {
        PlanCommentReqDto planCommentReqDto1 = new PlanCommentReqDto("comment1입니다.", init_plan.getId(), null, null);
        PlanComment planComment = planCommentService.save(planCommentReqDto1, init_member1.getId());

        assertEquals("comment 갯수는 1개여야 합니다.", 1, planCommentService.findAll(init_plan.getId()).size());
        assertEquals("plan의 comment 갯수는 1개여야 합니다.", 1, init_plan.getPlanComments().size());
//        assertEquals("planCommentUser의 갯수는 1개여야 합니다.", 1, planComment.getCreatedUser().getPlanCommentUser().size());

        PlanCommentReqDto planCommentReqDto2 = new PlanCommentReqDto("comment1의 자식입니다.",
                init_plan.getId(), planComment.getId(), null);
        planCommentService.save(planCommentReqDto2, init_member1.getId());

        assertEquals("comment 갯수는 2개여야 합니다.", 2, planCommentService.findAll(init_plan.getId()).size());
        assertEquals("plan의 comment 갯수는 2개여야 합니다.", 2, init_plan.getPlanComments().size());
//        assertEquals("planCommentUser의 갯수는 2개여야 합니다.", 2, planComment.getCreatedUser().getPlanCommentUser().size());
    }

    @Test
    public void update() {
        PlanCommentReqDto planCommentReqDto1 = new PlanCommentReqDto("comment1입니다.", init_plan.getId(), null, null);
        PlanComment planComment = planCommentService.save(planCommentReqDto1, init_member1.getId());
        assertEquals("plan comment의 내용이 같아야 합니다.", "comment1입니다.", planComment.getContent());

        PlanCommentReqDto planCommentReqDto2 = new PlanCommentReqDto("comment1입니다-update", null, null, planComment.getId());
        planComment.updatePlanComment(planCommentReqDto2.getContent());
        assertEquals("plan comment의 내용이 바뀌어야 합니다.", "comment1입니다-update", planComment.getContent());
    }

    @Test
    public void delete() {

        PlanCommentReqDto planCommentReqDto1 = new PlanCommentReqDto("comment1입니다.", init_plan.getId(), null, null);
        PlanComment planComment = planCommentService.save(planCommentReqDto1, init_member1.getId());

        PlanCommentReqDto planCommentReqDto2 = new PlanCommentReqDto("comment1의 자식입니다.",
                init_plan.getId(), planComment.getId(), null);
        planCommentService.save(planCommentReqDto2, init_member1.getId());

        assertEquals("comment 갯수는 2개여야 합니다.", 2, planCommentService.findAll(init_plan.getId()).size());
        assertEquals("plan의 comment 갯수는 2개여야 합니다.", 2, init_plan.getPlanComments().size());
//        assertEquals("planCommentUser의 갯수는 2개여야 합니다.", 2, planComment.getCreatedUser().getPlanCommentUser().size());

        planCommentService.delete(planComment.getId());

        init_plan = planService.findOne(init_plan.getId());

        assertEquals("comment 갯수는 0개여야 합니다.", 0, planCommentService.findAll(init_plan.getId()).size());
        assertEquals("plan의 comment 갯수는 0개여야 합니다.", 0, init_plan.getPlanComments().size());
//        assertEquals("planCommentUser의 갯수는 0개여야 합니다.", 0, planComment.getCreatedUser().getPlanCommentUser().size());
    }

    @Test
//    @Commit
    public void deletePlan(){
        PlanCommentReqDto planCommentReqDto1 = new PlanCommentReqDto("comment1입니다.", init_plan.getId(), null, null);
        PlanComment planComment = planCommentService.save(planCommentReqDto1, init_member1.getId());

        PlanCommentReqDto planCommentReqDto2 = new PlanCommentReqDto("comment1의 자식입니다.",
                init_plan.getId(), planComment.getId(), null);
        planCommentService.save(planCommentReqDto2, init_member1.getId());

        planService.delete(init_plan.getId());

        List<Plan> afterPlans = planService.findAll(init_category.getId());

//        for (PlanComment pm : init_member1.getPlanCommentUser()){
//            System.out.println("=========================");
//            System.out.println(pm.getContent());
//            System.out.println(pm.getPlan().getTitle());
//        }

        init_category = categoryService.findOne(init_category.getId());

        assertEquals("category 의 plan 갯수는 0개여야 한다.", 0, init_category.getPlans().size());
        assertEquals("plan 갯수는 0개여야 한다.", 0, afterPlans.size());
    }

}