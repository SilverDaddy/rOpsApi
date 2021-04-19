package infra.rOpsApi.job.controller;

import infra.rOpsApi.job.model.Job;
import infra.rOpsApi.job.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobRepository jobRepository;

    @GetMapping
    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    // 헬스체크 Job 제외
    @GetMapping("/selectbox")
    public List<Job> getJobsSelect() {
        return jobRepository.getJobsSelect();
    }

    @GetMapping("/{job_id}")
    public Job getJobById(@PathVariable("job_id") String id) {
        return jobRepository.findById(id).get();
    }
}