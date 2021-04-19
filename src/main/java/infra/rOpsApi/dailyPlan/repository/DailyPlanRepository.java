package infra.rOpsApi.dailyPlan.repository;

import infra.rOpsApi.dailyPlan.model.DailyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyPlanRepository extends JpaRepository<DailyPlan, Long> {
    List<DailyPlan> findAllByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
}