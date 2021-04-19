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
public class DailyReportBotDTO {
    String bot_id;
    BigInteger count_total;
    String totaluptime;
}