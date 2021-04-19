package infra.rOpsApi.admin.step.controller;

import infra.rOpsApi.step.model.Step;
import infra.rOpsApi.step.model.Step_PK;
import infra.rOpsApi.step.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/steps")
public class StepAdminController {
    @Autowired
    private StepRepository stepRepository;

    @GetMapping
    public List<Step> getSteps() {
        return stepRepository.findAll();
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

    @PostMapping("/{job_id}")
    public Step insertStep(@PathVariable("job_id") String job_id, @RequestBody Step param) {
        Step step = new Step();
        step.setJob_id(job_id);
        step.setStep_id(param.getStep_id());
        step.setStep_name(param.getStep_name());
        step.setDescription(param.getDescription());

        return stepRepository.save(step);
    }

    @PatchMapping("/{job_id}/{step_id}")
    public Step updateStep(@PathVariable("job_id") String job_id, @PathVariable("step_id") String step_id, @RequestBody Step param) {
        Step_PK step_PK = new Step_PK(job_id, step_id);
        Step step = stepRepository.findById(step_PK).get();
        step.setStep_name(param.getStep_name());
        step.setDescription(param.getDescription());

        return stepRepository.save(step);
    }

    @DeleteMapping("/{job_id}/{step_id}")
    public void deleteStep(@PathVariable("job_id") String job_id, @PathVariable("step_id") String step_id) {
        Step_PK step_PK = new Step_PK(job_id, step_id);
        stepRepository.deleteById(step_PK);
    }
}