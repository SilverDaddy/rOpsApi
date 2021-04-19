package infra.rOpsApi.kpi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KPIChartDTO {
    String chart_axis;
    BigDecimal working_time;
}