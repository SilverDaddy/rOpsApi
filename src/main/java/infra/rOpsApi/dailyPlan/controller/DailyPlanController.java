package infra.rOpsApi.dailyPlan.controller;

import infra.rOpsApi.dailyPlan.model.DailyPlan;
import infra.rOpsApi.dailyPlan.model.DailyPlanDto;
import infra.rOpsApi.dailyPlan.model.DoingDTO;
import infra.rOpsApi.dailyPlan.model.DoneDTO;
import infra.rOpsApi.dailyPlan.repository.DailyPlanRepository;
import infra.rOpsApi.dailyPlan.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/dailyPlan")
public class DailyPlanController {
    @Autowired
    private DailyPlanRepository dailyPlanRepository;
    @Autowired
    private TodoListRepository todoListRepository;

    @GetMapping("/todo")
    public List<DailyPlan> getTodo(@Param("searchDate") String searchDate) {
        LocalDateTime start_date = LocalDateTime.parse(searchDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(searchDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return dailyPlanRepository.findAllByCreatedDateBetween(start_date, end_date);
    }

    @GetMapping("/doing")
    public List<DoingDTO> getDoing(@Param("searchDate") String searchDate) {
        LocalDateTime start_date = LocalDateTime.parse(searchDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(searchDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return todoListRepository.getDoing(start_date, end_date);
    }

    @GetMapping("/done")
    public List<DoneDTO> getDone(@Param("searchDate") String searchDate) {
        LocalDateTime start_date = LocalDateTime.parse(searchDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(searchDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return todoListRepository.getDone(start_date, end_date);
    }

    @PostMapping
    public Long insertDailyPlan(DailyPlanDto param) {
        return dailyPlanRepository.save(param.toEntity()).getSeq();
       // return dailyPlanRepository.save(new DailyPlan(param.getSeq(), param.getSchedule_name(), param.getSchedule_time(), today));
    }
}
