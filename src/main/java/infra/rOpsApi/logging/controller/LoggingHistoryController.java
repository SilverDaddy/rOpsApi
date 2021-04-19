package infra.rOpsApi.logging.controller;

import infra.rOpsApi.logging.model.*;
import infra.rOpsApi.logging.repository.LoggingHistoryRepository;
import infra.rOpsApi.logging.repository.LoggingHistoryStaticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/logging")
public class LoggingHistoryController {
    @Autowired
    private LoggingHistoryRepository loggingHistoryRepository;

    @Autowired
    private LoggingHistoryStaticRepository loggingHistoryStaticRepository;

    // 업무 별 통계
    @GetMapping
    public List<LoggingHistoryDTO> getLogging(@Param("bots") String bots, @Param("jobs") String jobs, @Param("startDate") String startDate, @Param("endDate") String endDate) {
        List<String> botList = Arrays.asList(bots.split(","));
        List<String> jobList = Arrays.asList(jobs.split(","));
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return loggingHistoryStaticRepository.getLogging(botList, jobList, start_date, end_date);
    }

   /* @GetMapping
    public List<LoggingHistory> getLogging(@Param("bots") String bots, @Param("jobs") String jobs, @Param("startDate") String startDate, @Param("endDate") String endDate) {
        List<String> botList = Arrays.asList(bots.split(","));
        List<String> jobList = Arrays.asList(jobs.split(","));
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return loggingHistoryRepository.getLogging(botList, jobList, start_date, end_date);
    }*/

    @GetMapping("/{bot_id}")
    public List<LoggingHistory> getLoggingByBotId(@PathVariable("bot_id") String bot_id) {
        return loggingHistoryRepository.getLoggingByBotId(bot_id);
    }

    @GetMapping("/{bot_id}/{job_id}")
    public List<LoggingHistory> getLoggingByBotId_JobId(@PathVariable("bot_id") String bot_id, @PathVariable("job_id") String job_id) {
        if (bot_id.equals("ALL")) {
            return loggingHistoryRepository.getLoggingByBotId_JobId(job_id);
        } else {
            return loggingHistoryRepository.getLoggingByBotId_JobId(bot_id, job_id);
        }
    }

    @GetMapping("/{bot_id}/{job_id}/{seq_no}")
    public List<LoggingHistory> getLoggingByBotId_JobId_SeqNo(@PathVariable("bot_id") String bot_id, @PathVariable("job_id") String job_id, @PathVariable("seq_no") int seq_no) {
        return loggingHistoryRepository.getLoggingByBotId_JobId_SeqNo(bot_id, job_id, seq_no);
    }

    @PostMapping
    public LoggingHistory insertLoggingHistory(@RequestBody LoggingHistory param) {
        param.setRun_date(LocalDateTime.now());

        // 봇, 업무 별 seq_no 추출
        int seq_no = loggingHistoryRepository.getSeqNo(param.getBot_id(),param.getJob_id());
        if (param.getStep_id().equals("000")) {
            seq_no = seq_no+1;
        }
        param.setSeq_no(seq_no);

        return loggingHistoryRepository.save(param);
    }

    // 대시보드 - 봇
    @GetMapping("/static/botAll")
    public List<DashboardBotDTO> getLoggingStaticBotALL(@Param("startDate") String startDate, @Param("endDate") String endDate) {
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return loggingHistoryStaticRepository.getLoggingStaticBotAll(start_date, end_date);
    }

    // 대시보드 - 업무
    @GetMapping("/static/jobAll")
    public List<DashboardJobDTO> getLoggingStaticJobALL(@Param("startDate") String startDate, @Param("endDate") String endDate) {
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return loggingHistoryStaticRepository.getLoggingStaticJobAll(start_date, end_date);
    }

    // 봇 별 통계
    @GetMapping("/static/bot")
    public List<LoggingHistoryStaticBotDTO> getLoggingStaticBot(@Param("startDate") String startDate, @Param("endDate") String endDate) {
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return loggingHistoryStaticRepository.getLoggingStaticBot(start_date, end_date);
    }

    // 업무 별 통계
    @GetMapping("/static/job")
    public List<LoggingHistoryStaticJobDTO> getLoggingStaticJob(@Param("startDate") String startDate, @Param("endDate") String endDate) {
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return loggingHistoryStaticRepository.getLoggingStaticJob(start_date, end_date);
    }
}