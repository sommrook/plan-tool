package com.project.plan.service;

import com.project.plan.domain.Category;
import com.project.plan.domain.Member;
import com.project.plan.domain.Project;
import com.project.plan.dto.CategoryReqDto;
import com.project.plan.repository.CategoryRepository;
import com.project.plan.repository.MemberRepository;
import com.project.plan.repository.ProjectRepository;
import com.project.plan.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    private final CategoryRepository categoryRepository;

    public Category save(CategoryReqDto categoryReqDto, Long memberId){
        Member member = memberRepository.findById(memberId);
        Project project = projectRepository.findById(categoryReqDto.getProjectId());
        // 1. 중복체크
        duplicateCheck(categoryReqDto.getName(), project);
        // 2. 영속화
        Category category = Category.createCategory(categoryReqDto, project, member);
        categoryRepository.save(category);
        return category;
    }

    public void update(Long categoryId, CategoryReqDto categoryReqDto, Long memberId){
        Member member = memberRepository.findById(memberId);
        Category category = categoryRepository.findById(categoryId);
        duplicateCheck(categoryReqDto.getName(), category.getProject());

        category.updateCategory(categoryReqDto, member);
    }

    public List<Category> findAll(Long projectId){
        Project project = projectRepository.findById(projectId);
        return categoryRepository.findAll(project);
    }

    public void delete(Long categoryId){
        Category category = categoryRepository.findById(categoryId);
        category.removeCategory();
        categoryRepository.delete(category);
    }

    private void duplicateCheck(String categoryName, Project project){
        List<Category> category = categoryRepository.findByName(project, categoryName);
        if (!category.isEmpty()){
            throw new IllegalStateException("이미 존재하는 카테고리명입니다.");
        }
    }
}
