package infra.rOpsApi.logging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggingHistoryManagementDTO {
    String bot_id;
    String bot_name;
    String job_id;
    String job_name;
    int seq_no;
    String step_id;
    String step_name;
    String run_date;
}
