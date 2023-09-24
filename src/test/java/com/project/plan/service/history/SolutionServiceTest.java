package com.project.plan.service.history;

import com.project.plan.domain.Solution;
import com.project.plan.dto.SolutionReqDto;
import com.project.plan.service.SolutionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SolutionServiceTest {

    @Autowired
    SolutionService solutionService;

    @Before
    public void createSolution(){
        System.out.println("create solution at before test");

        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution1", "solution1");
        solutionService.save(solutionReqDto);
    }

    @DisplayName("중복 없는 솔루션 저장")
    @Test
    public void save() throws Exception{

        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution2", "solution2");
        solutionService.save(solutionReqDto);

        List<Solution> solutions = solutionService.findAll();

        assertEquals("솔루션 갯수는 2개여야 한다.", 2, solutions.size());
    }

    @DisplayName("중복 있는 솔루션 저장, 예외")
    @Test(expected = IllegalStateException.class)
    public void saveException() {
        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution1", "solution2");

        solutionService.save(solutionReqDto);

        fail("예외가 발생해야 한다.");
    }

    @DisplayName("솔루션 업데이트")
    @Test
    public void update() {
        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution2", "solution2");
        Solution solution = solutionService.save(solutionReqDto);

        SolutionReqDto updateSolutionDto = new SolutionReqDto("Solution-Update", "solution update test");
        solution.updateSolution(updateSolutionDto);

        assertEquals("업데이트 된 이름과 일치해야 한다.", "Solution-Update", solution.getName());
        assertEquals("업데이트 된 내용과 일치해야 한다.", "solution update test", solution.getDetail());
    }

    @DisplayName("솔루션 삭제 테스트")
    @Test
    public void delete(){
        SolutionReqDto solutionReqDto = new SolutionReqDto("Solution2", "solution2");
        Solution solution = solutionService.save(solutionReqDto);

        solutionService.delete(solution.getId());

        List<Solution> solutions = solutionService.findAll();

        assertEquals("삭제 후 레코드 갯수는 1개여야 한다.", 1, solutions.size());
    }
}