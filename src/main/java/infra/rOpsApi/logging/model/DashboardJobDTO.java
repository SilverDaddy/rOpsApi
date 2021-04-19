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
public class DashboardJobDTO {
    String job_name;
    String bot_id;
    String step_name;
    BigInteger count_total;
    BigDecimal rate_success;
    String totaluptime;
    Time avgtime;
    String status_progress;
    String run_date;
}
