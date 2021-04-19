package infra.rOpsApi.kpi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KPICountDTO {
    BigInteger count_job;
    BigDecimal count_run;
    String working_time;
}