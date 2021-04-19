package infra.rOpsApi.dailyPlan.model;

import infra.rOpsApi.common.entity.TimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "daily_plan")
public class DailyPlan extends TimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long seq;

    @Column(length = 100, nullable = false)
    private String schedule_name;

    @Column(length = 20, nullable = false)
    private String schedule_time;

    @Column(length = 100, nullable = false)
    private String schedule_file;

    @Builder
    public DailyPlan(Long seq, String schedule_name, String schedule_time, String schedule_file) {
        this.seq = seq;
        this.schedule_name = schedule_name;
        this.schedule_time = schedule_time;
        this.schedule_file = schedule_file;
    }
}