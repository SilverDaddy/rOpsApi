package infra.rOpsApi.dailyPlan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoingDTO {
    String job_name;
    String schedule_time;
    String run_date;
}