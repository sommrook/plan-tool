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
        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution1", "solution1");
        Solution solution = solutionService.save(solutionReqDto);

        ProjectReqDto projectReqDto = new ProjectReqDto("Project1", "Project Test", solution.getId());
        Project project = projectService.save(projectReqDto);

        List<Project> projects = solution.getProjects();

        assertEquals("생성 후 프로젝트의 갯수는 1개이다.", 1, projects.size());
    }

}