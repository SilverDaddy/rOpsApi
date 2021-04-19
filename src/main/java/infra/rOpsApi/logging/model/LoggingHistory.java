package infra.rOpsApi.logging.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import infra.rOpsApi.bot.model.Bot;
import infra.rOpsApi.code.model.Code;
import infra.rOpsApi.job.model.Job;
import infra.rOpsApi.step.model.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "logging_history")
@IdClass(LoggingHistory_PK.class)
public class LoggingHistory {
    @Id
    String bot_id;
    @Id
    String job_id;
    @Id
    String step_id;
    @Id
    int seq_no;
    int code_id_status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime run_date;
    String description;

    // 검색 용
    @Transient
    private List<String> bots;
    @Transient
    private List<String> jobs;
    @Transient
    LocalDateTime startDate;
    @Transient
    LocalDateTime endDate;

    @ManyToOne(targetEntity = Bot.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "bot_id", insertable = false, updatable = false)
    private Bot bot;

    @ManyToOne(targetEntity = Job.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Job job;

    @ManyToOne(targetEntity = Step.class, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "job_id", insertable = false, updatable = false),
            @JoinColumn(name = "step_id", insertable = false, updatable = false)
    })
    private Step step;

    @ManyToOne(targetEntity = Code.class, fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(formula=@JoinFormula(value="1", referencedColumnName="code_type_id")),
        @JoinColumnOrFormula(column = @JoinColumn(name="code_id_status", referencedColumnName="code_id", insertable = false, updatable = false))
    })
    private Code status;
}