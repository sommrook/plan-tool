package com.project.plan.service;

import com.project.plan.domain.Project;
import com.project.plan.domain.Solution;
import com.project.plan.dto.ProjectReqDto;
import com.project.plan.dto.SolutionReqDto;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProjectServiceTest {

    @Autowired SolutionService solutionService;

    @Autowired ProjectService projectService;

    @DisplayName("프로젝트 생성 테스트")
    @Test
    @Commit
    public void save(){
        SolutionReqDto solutionReqDto1 = new SolutionReqDto("Solution1", "solution1");
        Solution solution1 = solutionService.save(solutionReqDto1);

        SolutionReqDto solutionReqDto2 = new SolutionReqDto("Solution2", "solution2");
        Solution solution2 = solutionService.save(solutionReqDto2);

        ProjectReqDto projectReqDto1 = new ProjectReqDto("Project1", "Project Test", solution1.getId());
        projectService.save(projectReqDto1);

        ProjectReqDto projectReqDto2 = new ProjectReqDto("Project1", "Project Test", solution2.getId());
        projectService.save(projectReqDto2);

        List<Project> projects1 = solution1.getProjects();
        List<Project> projects2 = solution2.getProjects();

        assertEquals("생성 후 프로젝트1의 갯수는 1개이다.", 1, projects1.size());
        assertEquals("생성 후 프로젝트2의 갯수는 1개 이다.", 1, projects2.size());
    }

    @DisplayName("프로젝트 생성 예외 테스트")
    @Test(expected = IllegalStateException.class)
    public void saveException(){
        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution1", "solution1");
        Solution solution = solutionService.save(solutionReqDto);

        ProjectReqDto projectReqDto1 = new ProjectReqDto("Project1", "Project Test", solution.getId());
        projectService.save(projectReqDto1);

        ProjectReqDto projectReqDto2 = new ProjectReqDto("Project1", "Project Test", solution.getId());
        projectService.save(projectReqDto2);

        fail("예외가 발생해야 한다.");
    }

    @DisplayName("프로젝트 수정 테스트")
    @Test
    public void update(){
        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution1", "solution1");
        Solution solution = solutionService.save(solutionReqDto);

        ProjectReqDto projectReqDto = new ProjectReqDto("Project1", "Project Test", solution.getId());
        Project project = projectService.save(projectReqDto);

        ProjectReqDto updateReqDto = new ProjectReqDto("Project2", "Project Update Test", null);
        project.updateProject(updateReqDto);

        assertEquals("프로젝트명이 바뀌어야 한다.", "Project2", project.getName());
        assertEquals("프로젝트 내용이 바뀌어야 한다.", "Project Update Test", project.getDetail());
        assertEquals("solution 정보는 그 전과 동일해야 한다.", solution, project.getSolution());

    }


    @DisplayName("삭제 테스트")
    @Test
    @Commit
    public void delete(){

//        Solution findSolution = solutionService.findById(2L);
//        solutionService.delete(findSolution);
        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution1", "solution1");
        Solution solution = solutionService.save(solutionReqDto);

        ProjectReqDto projectReqDto1 = new ProjectReqDto("Project1", "Project Test", solution.getId());
        Project project1 = projectService.save(projectReqDto1);

        ProjectReqDto projectReqDto2 = new ProjectReqDto("Project2", "Project Test", solution.getId());
        projectService.save(projectReqDto2);

        projectService.delete(project1);

        List<Project> solutionProjects = solution.getProjects();
        List<Project> projects = projectService.findAll(solution);

        assertEquals("프고젝트 갯수는 1개여야 한다.", 1, projects.size());
        assertEquals("프로젝트 갯수는 1개여야 한다.(solution-projects)", 1, solutionProjects.size());

        solutionService.delete(solution);
    }

}