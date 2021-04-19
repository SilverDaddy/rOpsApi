package infra.rOpsApi.job.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_master")
public class Job {
    @Id
    String job_id;
    String job_name;
    @ColumnDefault("0")
    Integer working_time;
    String description;
}