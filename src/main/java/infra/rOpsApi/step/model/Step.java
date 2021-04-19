package infra.rOpsApi.step.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import infra.rOpsApi.job.model.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "step_master")
@IdClass(Step_PK.class)
public class Step {
    @Id
    String job_id;
    @Id
    String step_id;
    String step_name;
    String description;

    @ManyToOne(targetEntity = Job.class)
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Job job;
}