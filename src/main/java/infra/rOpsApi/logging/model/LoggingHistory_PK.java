package infra.rOpsApi.logging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoggingHistory_PK implements Serializable {
    private String bot_id;
    private String job_id;
    private String step_id;
    private Integer seq_no;
}