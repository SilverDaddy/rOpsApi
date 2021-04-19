package infra.rOpsApi.logging.repository;

import infra.rOpsApi.logging.model.LoggingHistory;
import infra.rOpsApi.logging.model.LoggingHistoryManagementDTO;
import infra.rOpsApi.logging.model.LoggingHistory_PK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggingHistoryRepository extends JpaRepository<LoggingHistory, LoggingHistory_PK> {

    @Query("SELECT lh FROM LoggingHistory lh JOIN FETCH lh.bot JOIN FETCH lh.job JOIN FETCH lh.step JOIN FETCH lh.status WHERE lh.bot_id = ?1")
    List<LoggingHistory> getLoggingByBotId(String bot_id);

    @Query("SELECT lh FROM LoggingHistory lh JOIN FETCH lh.bot JOIN FETCH lh.job JOIN FETCH lh.step JOIN FETCH lh.status WHERE lh.job_id = ?1")
    List<LoggingHistory> getLoggingByBotId_JobId(String job_id);

    @Query("SELECT lh FROM LoggingHistory lh JOIN FETCH lh.bot JOIN FETCH lh.job JOIN FETCH lh.step JOIN FETCH lh.status WHERE lh.bot_id = ?1 AND lh.job_id = ?2")
    List<LoggingHistory> getLoggingByBotId_JobId(String bob_id, String job_id);

    @Query("SELECT lh FROM LoggingHistory lh JOIN FETCH lh.bot JOIN FETCH lh.job JOIN FETCH lh.step JOIN FETCH lh.status WHERE lh.bot_id = ?1 AND lh.job_id = ?2 AND lh.seq_no = ?3")
    List<LoggingHistory> getLoggingByBotId_JobId_SeqNo(String bob_id, String job_id, int seq_no);

    @Query(
            value ="DELETE FROM logging_history " +
                    "WHERE bot_id = ?1 ",
            nativeQuery = true)
    Void deleteLoggingHistoryByBotId(String bob_id);

    @Query(
            value ="DELETE FROM logging_history " +
                    "WHERE bot_id = ?1 AND job_id = ?2 ",
            nativeQuery = true)
    Void deleteLoggingHistoryByBotId_JobId(String bob_id, String job_id);

    @Query(
            value ="DELETE FROM logging_history " +
                    "WHERE bot_id = ?1 AND job_id = ?2 AND seq_no = ?3 ",
            nativeQuery = true)
    Void deleteLoggingHistoryByBotId_JobId_SeqNo(String bob_id, String job_id, int seq_no);

    // 하루전 헬스체크 이력 삭제
    @Query(
            value ="DELETE FROM logging_history " +
                    "WHERE job_id = '000-0' AND DATE_SUB(NOW(), INTERVAL 1 DAY) > run_date ",
            nativeQuery = true)
    Void deleteLoggingHistoryHealthCheck();

    @Query(
            value ="SELECT IFNULL(MAX(seq_no),0) FROM logging_history WHERE bot_id = ?1 AND job_id = ?2",
            nativeQuery = true)
    Integer getSeqNo(String bob_id, String job_id);
}