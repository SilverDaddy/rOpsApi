package infra.rOpsApi.dailyReport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReportCountDTO {
    BigInteger count_total;
    BigDecimal count_success;
    BigDecimal count_fail;
    BigDecimal rate_success;
}