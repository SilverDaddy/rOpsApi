package infra.rOpsApi.dailyReport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyReportUtilizationDTO {
    String bot_id;
    BigDecimal rate_utilization;
}