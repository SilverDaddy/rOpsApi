package infra.rOpsApi.job.repository;

import infra.rOpsApi.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {

    @Query(
            value ="SELECT job_id" +
                    ", job_name" +
                    ", working_time" +
                    ", description " +
                    "FROM job_master " +
                    "WHERE job_id != '000-0'; ",
            nativeQuery = true)
    List<Job> getJobsSelect();
}