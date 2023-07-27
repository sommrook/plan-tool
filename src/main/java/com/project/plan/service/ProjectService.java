package com.project.plan.service;

import com.project.plan.domain.Project;
import com.project.plan.domain.Solution;
import com.project.plan.dto.ProjectReqDto;
import com.project.plan.repository.ProjectRepository;
import com.project.plan.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final SolutionRepository solutionRepository;

    private final ProjectRepository projectRepository;


    public Project save(ProjectReqDto projectReqDto){
        Solution solution = solutionRepository.findById(projectReqDto.getSolutionId());
        duplicateCheck(projectReqDto.getName(), solution);
        Project project = Project.createProject(solution, projectReqDto);
        projectRepository.save(project);

        return project;
    }

    public void update(Long projectId, ProjectReqDto projectReqDto){
        Project project = projectRepository.findById(projectId);
        duplicateCheck(projectReqDto.getName(), project.getSolution());
        project.updateProject(projectReqDto);
    }

    public void delete(Project project){
        project.removeProject();
        projectRepository.delete(project);
    }

    public List<Project> findAll(Solution solution){
        return projectRepository.findAll(solution);
    }

    private void duplicateCheck(String projectName, Solution solution) {
        List<Project> findProject = projectRepository.findByName(solution, projectName);
        if (!findProject.isEmpty()){
            throw new IllegalStateException("이미 존재하는 프로젝트명입니다.");
        }
    }
}
