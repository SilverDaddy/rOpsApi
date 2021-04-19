package infra.rOpsApi.dailyPlan.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DailyPlanDto {
    private Long seq;
    private String schedule_name;
    private String schedule_time;
    private String schedule_file;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public DailyPlan toEntity(){
        DailyPlan DailyPlanEntity = DailyPlan.builder()
                .seq(seq)
                .schedule_name(schedule_name)
                .schedule_time(schedule_time)
                .schedule_file(schedule_file)
                .build();
        return DailyPlanEntity;
    }

    @Builder
    public DailyPlanDto(Long seq, String schedule_name, String schedule_time, String schedule_file, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.seq = seq;
        this.schedule_name = schedule_name;
        this.schedule_time = schedule_time;
        this.schedule_file = schedule_file;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}