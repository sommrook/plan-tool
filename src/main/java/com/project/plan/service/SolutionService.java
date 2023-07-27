package com.project.plan.service;

import com.project.plan.domain.Solution;
import com.project.plan.dto.SolutionReqDto;
import com.project.plan.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public Solution save(SolutionReqDto solutionReqDto){
        // 1. 중복 체크
        // 2. instance 저장
        // 3. 영속
        duplicateCheck(solutionReqDto.getName());
        Solution solution = Solution.createSolution(solutionReqDto);
        solutionRepository.save(solution);
        return solution;
    }

    public void update(Long solutionId, SolutionReqDto solutionReqDto){
        duplicateCheck(solutionReqDto.getName());
        Solution solution = solutionRepository.findById(solutionId);
        solution.updateSolution(solutionReqDto);
    }

    public Solution findById(Long solutionId){
        return solutionRepository.findById(solutionId);
    }

    public List<Solution> findAll(){
        return solutionRepository.findAll();
    }

    public void delete(Solution solution){
        solutionRepository.delete(solution);
    }

    private void duplicateCheck(String solutionName) {
        List<Solution> findByName = solutionRepository.findByName(solutionName);
        if (!findByName.isEmpty()){
            throw new IllegalStateException("이미 저장된 solution 이름이 있습니다.");
        }
    }
}
