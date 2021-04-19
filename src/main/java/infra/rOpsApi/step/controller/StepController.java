package infra.rOpsApi.step.controller;

import infra.rOpsApi.step.model.Step;
import infra.rOpsApi.step.model.Step_PK;
import infra.rOpsApi.step.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/steps")
public class StepController {
    @Autowired
    private StepRepository stepRepository;

    @GetMapping
    public List<Step> getSteps() {
        return stepRepository.getSteps();
    }

    @GetMapping("/{job_id}")
    public List<Step> getStepByJobId(@PathVariable("job_id") String id) {
        return stepRepository.getStepByJobId(id);
    }

    @GetMapping("/{job_id}/{step_id}")
    public Step getStepByJobId_StepId(@PathVariable("job_id") String job_id, @PathVariable("step_id") String step_id) {
        Step_PK step_PK = new Step_PK(job_id, step_id);
        return stepRepository.findById(step_PK).get();
    }
}