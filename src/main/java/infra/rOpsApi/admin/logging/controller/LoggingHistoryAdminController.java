package infra.rOpsApi.admin.logging.controller;

import infra.rOpsApi.logging.model.LoggingHistory;
import infra.rOpsApi.logging.model.LoggingHistoryManagementDTO;
import infra.rOpsApi.logging.model.LoggingHistory_PK;
import infra.rOpsApi.logging.repository.LoggingHistoryRepository;
import infra.rOpsApi.logging.repository.LoggingHistoryStaticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/logging")
public class LoggingHistoryAdminController {
    @Autowired
    private LoggingHistoryRepository loggingHistoryRepository;

    @Autowired
    private LoggingHistoryStaticRepository loggingHistoryStaticRepository;

    // 히스토리 테이블 관리
    @GetMapping("/management")
    public List<LoggingHistoryManagementDTO> getLoggingManagement() {

        return loggingHistoryStaticRepository.getLoggingManagement();
    }

    @PatchMapping("/{bot_id}/{job_id}/{seq_no}/{step_id}")
    public LoggingHistory updateLoggingHistory(@PathVariable("bot_id") String bot_id, @PathVariable("job_id") String job_id,
                                               @PathVariable("seq_no") int seq_no, @PathVariable("step_id") String step_id, @RequestBody LoggingHistory param) {
        LoggingHistory_PK loggingHistory_PK = new LoggingHistory_PK(bot_id, job_id, step_id, seq_no);

        LoggingHistory loggingHistory = loggingHistoryRepository.findById(loggingHistory_PK).get();
        loggingHistory.setCode_id_status(param.getCode_id_status());
        loggingHistory.setDescription(param.getDescription());

        return loggingHistoryRepository.save(loggingHistory);
    }

    // 1개 이력 데이터 삭제
    @DeleteMapping("/{bot_id}/{job_id}/{seq_no}/{step_id}")
    public void deleteLoggingHistory(@PathVariable("bot_id") String bot_id, @PathVariable("job_id") String job_id,
                                     @PathVariable("seq_no") int seq_no, @PathVariable("step_id") String step_id) {
        LoggingHistory_PK loggingHistory_PK = new LoggingHistory_PK(bot_id, job_id, step_id, seq_no);

        loggingHistoryRepository.deleteById(loggingHistory_PK);
    }

    // 봇 별 이력 초기화
    @DeleteMapping("/{bot_id}")
    public void deleteLoggingHistoryByBotId(@PathVariable("bot_id") String bot_id) {
        loggingHistoryRepository.deleteLoggingHistoryByBotId(bot_id);
    }

    // 봇-업무 별 이력 초기화
    @DeleteMapping("/{bot_id}/{job_id}")
    public void deleteLoggingHistoryByBotId_JobId(@PathVariable("bot_id") String bot_id, @PathVariable("job_id") String job_id) {
        loggingHistoryRepository.deleteLoggingHistoryByBotId_JobId(bot_id, job_id);
    }

    // 봇-업무-작업회차 별 이력 초기화
    @DeleteMapping("/{bot_id}/{job_id}/{seq_no}")
    public void deleteLoggingHistoryByBotId_JobId(@PathVariable("bot_id") String bot_id, @PathVariable("job_id") String job_id, @PathVariable("seq_no") int seq_no) {
        loggingHistoryRepository.deleteLoggingHistoryByBotId_JobId_SeqNo(bot_id, job_id, seq_no);
    }

    // 하루전 헬스체크 이력 삭제
    @DeleteMapping("/healthcheck")
    public void deleteLoggingHistoryHealthCheck() {
        loggingHistoryRepository.deleteLoggingHistoryHealthCheck();
    }
}