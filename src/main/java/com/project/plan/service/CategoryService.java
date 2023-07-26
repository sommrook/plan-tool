package com.project.plan.service;

import com.project.plan.dto.CategoryReqDto;
import com.project.plan.repository.CategoryRepository;
import com.project.plan.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final SolutionRepository solutionRepository;

    private final CategoryRepository categoryRepository;

    public void saveCategory(CategoryReqDto categoryReqDto){
        // 1. 중복체크
        // 2. 영속화
    }
}
