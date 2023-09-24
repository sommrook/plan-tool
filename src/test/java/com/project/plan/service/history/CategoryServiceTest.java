package com.project.plan.service.history;

import com.project.plan.domain.*;
import com.project.plan.dto.CategoryReqDto;
import com.project.plan.dto.MemberReqDto;
import com.project.plan.dto.ProjectReqDto;
import com.project.plan.dto.SolutionReqDto;
import com.project.plan.repository.MemberRepository;
import com.project.plan.repository.ProjectRepository;
import com.project.plan.repository.SolutionRepository;
import com.project.plan.service.CategoryService;
import com.project.plan.service.MemberService;
import com.project.plan.service.ProjectService;
import com.project.plan.service.SolutionService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    SolutionService solutionService;
    @Autowired
    ProjectService projectService;
    @Autowired
    CategoryService categoryService;

    private Member init_member1;
    private Member init_member2;
    private Solution init_solution;
    private Project init_project1;
    private Project init_project2;


    @Before
    public void beforeSetting(){
        System.out.println("create Member");

        MemberReqDto memberReqDto = new MemberReqDto("user1", "1234", "chloe", "sommrook@gmail.com", "qa", Permission.ADMIN);
        MemberReqDto memberReqDto2 = new MemberReqDto("user2", "1243", "chloe2", "aaa@ssss.com", "qa", Permission.PLANNER);
        init_member1 = memberService.saveMember(memberReqDto);
        init_member2 = memberService.saveMember(memberReqDto2);

        SolutionReqDto solutionReqDto1 = new SolutionReqDto("Solution1", "solution1");
        SolutionReqDto solutionReqDto2 = new SolutionReqDto("Solution2", "solution2");
        init_solution = solutionService.save(solutionReqDto1);
        Solution solution2 = solutionService.save(solutionReqDto2);

        ProjectReqDto projectReqDto1 = new ProjectReqDto("Project1", "Project Test", init_solution.getId());
        ProjectReqDto projectReqDto2 = new ProjectReqDto("Project1", "Project Test", solution2.getId());
        init_project1 = projectService.save(projectReqDto1);
        init_project2 = projectService.save(projectReqDto2);

    }

    @Test(expected = IllegalStateException.class)
    public void save() {
        CategoryReqDto categoryReqDto = new CategoryReqDto("category1", "category1의 내용입니다.", init_project1.getId());

        categoryService.save(categoryReqDto, init_member1.getId());

        List<Category> categories = categoryService.findAll(init_project1.getId());

        assertEquals("카테고리 갯수는 1개여야 한다.", 1, categories.size());
        assertEquals("프로젝트의 카테고리 갯수도 1개여야 한다.", 1, init_project1.getCategories().size());

        categoryService.save(categoryReqDto, init_member1.getId());
        fail("카테고리 중복 문제로 인해 실패해야 한다.");
    }

    @Test
    public void update() {
        CategoryReqDto categoryReqDto = new CategoryReqDto("category1", "category1의 내용입니다.", init_project1.getId());
        Category category = categoryService.save(categoryReqDto, init_member1.getId());

        CategoryReqDto categoryReqDto2 = new CategoryReqDto("category2", "category2 update", null);
        categoryService.update(category.getId(), categoryReqDto2, init_member2.getId());

        assertEquals("카테고리명이 바뀌어야 한다.", "category2", category.getName());
        assertEquals("카테고리 내용이 바뀌어야 한다.", "category2 update", category.getDetail());
        assertEquals("project 정보는 바뀌면 안된다", init_project1, category.getProject());
        assertEquals("수정자가 바뀌어야 한다.", init_member2, category.getUpdatedUser());

    }

    @Test
    public void delete(){
        CategoryReqDto categoryReqDto = new CategoryReqDto("category1", "category1의 내용입니다.", init_project1.getId());
        Category category = categoryService.save(categoryReqDto, init_project1.getId());

        assertEquals("카테고리 갯수는 1개여야 한다.", 1, categoryService.findAll(init_project1.getId()).size());
        assertEquals("프로젝트 카테고리 갯수도 1개여야 한다.", 1, init_project1.getCategories().size());

        categoryService.delete(category.getId());

        init_project1 = projectService.findOne(init_project1.getId());

        assertEquals("카테고리 갯수는 0개여야 한다.", 0, categoryService.findAll(init_project1.getId()).size());
        assertEquals("프로젝트 카테고리 갯수도 0개여야 한다.", 0, init_project1.getCategories().size());

        CategoryReqDto categoryReqDto2 = new CategoryReqDto("category1", "category1의 내용입니다.", init_project1.getId());
        categoryService.save(categoryReqDto2, init_member1.getId());

        projectService.delete(init_project1.getId());
    }
}