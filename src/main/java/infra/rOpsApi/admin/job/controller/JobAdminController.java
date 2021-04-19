package infra.rOpsApi.admin.job.controller;

import infra.rOpsApi.job.model.Job;
import infra.rOpsApi.job.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/jobs")
public class JobAdminController {
    @Autowired
    private JobRepository jobRepository;

    @GetMapping
    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    @GetMapping("/{job_id}")
    public Job getJobById(@PathVariable("job_id") String id) {
        return jobRepository.findById(id).get();
    }

    @PostMapping
    public Job insertJob(@RequestBody Job param) {
        return jobRepository.save(new Job(param.getJob_id(), param.getJob_name(), param.getWorking_time(), param.getDescription()));
    }

    @PatchMapping("/{job_id}")
    public Job updateJob(@PathVariable("job_id") String id, @RequestBody Job param) {
        Job job = jobRepository.findById(id).get();
        job.setJob_name(param.getJob_name());
        job.setWorking_time(param.getWorking_time());
        job.setDescription(param.getDescription());
        return jobRepository.save(job);
    }

    @DeleteMapping("/{job_id}")
    public void deleteJob(@PathVariable("job_id") String id) {
        jobRepository.deleteById(id);
    }
}