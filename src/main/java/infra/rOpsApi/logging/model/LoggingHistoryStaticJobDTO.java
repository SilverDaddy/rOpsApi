package infra.rOpsApi.logging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggingHistoryStaticJobDTO {
    /* 통계 */
    String job_name;
    String bot_id;
    String step_name;
    BigInteger count_total;
    String rate_success;
    String totaluptime;
    Time avgtime;
    String status_progress;
    String run_date;
}
