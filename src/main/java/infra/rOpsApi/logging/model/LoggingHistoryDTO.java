package infra.rOpsApi.logging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggingHistoryDTO {
    String bot_id;
    String bot_name;
    String job_id;
    String job_name;
    String step_id;
    String step_name;
    String code_name;
    int seq_no;
    String run_date;
    String description;
    String elapsedTime;
}
