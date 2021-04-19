package infra.rOpsApi.logging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggingHistoryStaticBotDTO {
    /* 통계 */
    String bot_id;
    String bot_name;
    String job_name;
    String step_name;
    BigInteger count_total;
    String rate_success;
    String status_progress;
    String run_date;
    String totaluptime;
    Time avgtime;
}
