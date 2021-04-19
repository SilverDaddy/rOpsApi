package infra.rOpsApi.step.repository;

import infra.rOpsApi.step.model.Step;
import infra.rOpsApi.step.model.Step_PK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Step_PK> {
    @Query("SELECT st FROM Step st JOIN FETCH st.job WHERE st.job_id = ?1")
    List<Step> getStepByJobId(String job_id);

    @Query("SELECT st FROM Step st JOIN FETCH st.job")
    List<Step> getSteps();
}