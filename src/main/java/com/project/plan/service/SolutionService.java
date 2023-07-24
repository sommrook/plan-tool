package com.project.plan.service;

import com.project.plan.domain.Solution;
import com.project.plan.domain.dto.SolutionRequestDto;
import com.project.plan.repository.SolutionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public Solution save(SolutionRequestDto solutionRequestDto){
        // 1. 중복 체크
        // 2. instance 저장
        // 3. 영속
        duplicateCheck(solutionRequestDto.getName());
        Solution solution = Solution.createSolution(solutionRequestDto);
        solutionRepository.save(solution);
        return solution;
    }

    public void update(Long solutionId, SolutionRequestDto solutionRequestDto){
        Solution solution = solutionRepository.findById(solutionId);
        solution.updateSolution(solutionRequestDto);
    }

    public void delete(Long solutionId){
        solutionRepository.deleteById(solutionId);
    }

    private void duplicateCheck(String solutionName) {
        List<Solution> findByName = solutionRepository.findByName(solutionName);
        if (!findByName.isEmpty()){
            throw new IllegalStateException("이미 저장된 solution 이름이 있습니다.");
        }
    }
}
