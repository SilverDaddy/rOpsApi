package infra.rOpsApi.dailyReport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReportJobDTO {
    String job_name;
    BigInteger count_total;
    BigDecimal rate_success;
    String run_date;
}