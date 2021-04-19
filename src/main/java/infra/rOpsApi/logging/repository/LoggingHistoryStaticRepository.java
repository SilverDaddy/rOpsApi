package infra.rOpsApi.logging.repository;

import infra.rOpsApi.logging.model.*;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LoggingHistoryStaticRepository {
    @PersistenceContext
    private EntityManager em;

    // 히스토리 관리
    public List<LoggingHistoryManagementDTO> getLoggingManagement(){
        String sql ="SELECT lh.bot_id\n" +
                "\t, lh.bot_name\n" +
                "\t, lh.job_id\n" +
                "\t, lh.job_name\n" +
                "\t, lh.seq_no\n" +
                "\t, lh.step_id\n" +
                "\t, lh.step_name\n" +
                "\t, DATE_FORMAT(lh.run_date,'%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "FROM (\n" +
                "\tSELECT bm.bot_id\n" +
                "\t\t, bm.bot_name\n" +
                "\t\t, jm.job_id\n" +
                "\t\t, jm.job_name\n" +
                "\t\t, lh.seq_no\n" +
                "\t\t, MAX(sm.step_id) AS step_id\n" +
                "\t\t, sm.step_name\n" +
                "\t\t, cm.code_name\n" +
                "\t\t, MAX(lh.run_date) AS run_date   \n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN bot_master AS bm ON lh.bot_id = bm.bot_id\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id = jm.job_id\n" +
                "\tINNER JOIN step_master AS sm ON lh.job_id = sm.job_id AND lh.step_id = sm.step_id\n" +
                "\tINNER JOIN code_master AS cm ON 1 = cm.code_type_id AND lh.code_id_status = cm.code_id \n" +
                "\tWHERE lh.job_id <> '000-0'\n" +
                "\t\tAND lh.step_id <> 'WAIT'\n" +
                "\tGROUP BY bot_id, job_id, seq_no\n" +
                ") lh\n" +
                "WHERE lh.step_id <> '999' AND DATE_SUB(NOW(),INTERVAL 1 HOUR) > lh.run_date\n" +
                "ORDER BY run_date DESC";
        Query nativeQuery = em.createNativeQuery(sql);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<LoggingHistoryManagementDTO> result = jpaResultMapper.list(nativeQuery, LoggingHistoryManagementDTO.class);

        em.close();

        return result;
    }

    // 히스토리
    public List<LoggingHistoryDTO> getLogging(@Param("bots") List<String> bots, @Param("jobs") List<String> jobs, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql ="SELECT bm.bot_id\n" +
                "\t, bm.bot_name\n" +
                "\t, jm.job_id\n" +
                "\t, jm.job_name\n" +
                "\t, sm.step_id\n" +
                "\t, sm.step_name\n" +
                "\t, cm.code_name\n" +
                "\t, lh.seq_no\n" +
                "\t, DATE_FORMAT(lh.run_date, '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "\t, lh.description\n" +
                "\t, IFNULL(sec_to_time(timestampdiff(SECOND, lh.run_date, lh.next_time)),'-') AS elapsedTime\n" +
                "FROM (SELECT *\n" +
                "       , LAG(run_date)\n" +
                "         OVER(\n" +
                "           PARTITION BY bot_id, job_id, seq_no\n" +
                "           ORDER BY run_date DESC) AS next_time\n" +
                "       , First_value(run_date)\n" +
                "         over (\n" +
                "           PARTITION BY bot_id, job_id, seq_no\n" +
                "           ORDER BY run_date DESC ROWS BETWEEN unbounded preceding AND\n" +
                "         unbounded\n" +
                "         following) AS latest\n" +
                "\tFROM logging_history\n" +
                "\tWHERE bot_id IN :bots\n" +
                "\tAND job_id IN :jobs\n" +
                "\tAND run_date BETWEEN :startDate AND :endDate\n" +
                "\tAND job_id <> '000-0'\n" +
                "\tAND step_id <> 'WAIT'\n" +
                ") lh\n" +
                "INNER JOIN bot_master AS bm ON lh.bot_id = bm.bot_id\n" +
                "INNER JOIN job_master AS jm ON lh.job_id = jm.job_id\n" +
                "INNER JOIN step_master AS sm ON lh.job_id = sm.job_id AND lh.step_id = sm.step_id\n" +
                "INNER JOIN code_master AS cm ON 1 = cm.code_type_id AND lh.code_id_status = cm.code_id \n" +
                "ORDER BY lh.latest DESC, seq_no DESC, lh.step_id DESC";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("bots", bots);
        nativeQuery.setParameter("jobs", jobs);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<LoggingHistoryDTO> result = jpaResultMapper.list(nativeQuery, LoggingHistoryDTO.class);

        em.close();

        return result;
    }

    // 대시보드 - 봇 상태
    public List<DashboardBotDTO> getLoggingStaticBotAll(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql ="SELECT lh.bot_id\n" +
                "\t, bm.bot_name\n" +
                "\t, IFNULL((CASE WHEN DATE_SUB(NOW(), INTERVAL 1 HOUR) > lh_current_raw.run_date THEN '봇점검 필요' WHEN lh_current_raw.code_id_status = 3 THEN '대기중' ELSE CONCAT(lh_current_raw.job_name, ' 수행중') END),'봇점검 필요') AS status_progress\n" +
                "FROM logging_history AS lh\n" +
                "LEFT JOIN (\n" +
                "\tSELECT a.bot_id \n" +
                "\t\t, c.job_id\n" +
                "\t\t, d.step_id\n" +
                "\t\t, c.job_name\n" +
                "\t\t, d.step_name\n" +
                "\t\t, a.run_date\n" +
                "\t\t, a.code_id_status\n" +
                "\tFROM logging_history AS a\n" +
                "\tINNER JOIN (SELECT bot_id, MAX(run_date) AS cdate FROM logging_history WHERE run_date BETWEEN :startDate AND :endDate GROUP BY bot_id) AS b ON a.bot_id = b.bot_id AND a.run_date = b.cdate\n" +
                "\tINNER JOIN job_master AS c ON a.job_id = c.job_id\n" +
                "\tINNER JOIN step_master AS d ON a.job_id = d.job_id AND a.step_id = d.step_id\n" +
                "\tWHERE a.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY bot_id\n" +
                ") lh_current_raw on lh.bot_id = lh_current_raw.bot_id\n" +
                "INNER JOIN bot_master AS bm ON lh.bot_id = bm.bot_id\n" +
                "WHERE lh.run_date BETWEEN :startDate AND :endDate\n" +
                "GROUP BY bot_id";

        Query nativeQuery = em.createNativeQuery(sql);

        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DashboardBotDTO> result = jpaResultMapper.list(nativeQuery, DashboardBotDTO.class);

        em.close();

        return result;
    }

    // 봇 별 통계
    public List<LoggingHistoryStaticBotDTO> getLoggingStaticBot(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql ="SELECT lh.bot_id\n" +
                "\t, bm.bot_name\n" +
                "\t, lh_current_raw.job_name\n" +
                "\t, lh_current_raw.step_name\n" +
                "\t, CAST(SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) AS INT) AS count_total\n" +
                "\t, CONCAT(ROUND(SUM(CASE lh.code_id_status WHEN 4 then 1 else 0 end) / SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) * 100, 2),' %') AS rate_success\n" +
                "\t, IFNULL((CASE WHEN DATE_SUB(NOW(), INTERVAL 1 HOUR) > lh_current_raw2.run_date THEN '봇점검 필요' WHEN lh_current_raw2.code_id_status = 3 THEN '대기중' ELSE CONCAT(lh_current_raw.job_name, ' 수행중') END),'봇점검 필요') AS status_progress\n" +
                "\t, DATE_FORMAT(lh_current_raw2.run_date, '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "\t, lh_uptime.totaluptime\n" +
                "\t, SEC_TO_TIME(0) AS avgtime\n" +
                "FROM logging_history AS lh\n" +
                "LEFT JOIN (\n" +
                "\tSELECT a.bot_id \n" +
                "\t\t, c.job_id\n" +
                "\t\t, d.step_id\n" +
                "\t\t, c.job_name\n" +
                "\t\t, d.step_name\n" +
                "\t\t, a.run_date\n" +
                "\t\t, a.code_id_status\n" +
                "\tFROM logging_history AS a\n" +
                "\tINNER JOIN (SELECT bot_id, MAX(run_date) AS cdate FROM logging_history WHERE job_id <> '000-0' AND run_date BETWEEN :startDate AND :endDate GROUP BY bot_id) AS b ON a.bot_id = b.bot_id AND a.run_date = b.cdate\n" +
                "\tINNER JOIN job_master AS c ON a.job_id = c.job_id\n" +
                "\tINNER JOIN step_master AS d ON a.job_id = d.job_id AND a.step_id = d.step_id\n" +
                "\tWHERE a.run_date BETWEEN :startDate AND :endDate\n" +
                "\t\tAND a.job_id <> '000-0'\n" +
                "\tGROUP BY bot_id\n" +
                ") lh_current_raw on lh.bot_id = lh_current_raw.bot_id\n" +
                "LEFT JOIN (\n" +
                "\tSELECT a.bot_id \n" +
                "\t\t, a.run_date\n" +
                "\t\t, a.code_id_status\n" +
                "\tFROM logging_history AS a\n" +
                "\tINNER JOIN (SELECT bot_id, MAX(run_date) AS cdate FROM logging_history WHERE run_date BETWEEN :startDate AND :endDate GROUP BY bot_id) AS b ON a.bot_id = b.bot_id AND a.run_date = b.cdate\n" +
                "\tINNER JOIN job_master AS c ON a.job_id = c.job_id\n" +
                "\tINNER JOIN step_master AS d ON a.job_id = d.job_id AND a.step_id = d.step_id\n" +
                "\tWHERE a.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY bot_id\n" +
                ") lh_current_raw2 on lh.bot_id = lh_current_raw2.bot_id\n" +
                "INNER JOIN bot_master AS bm ON lh.bot_id = bm.bot_id\n" +
                "LEFT JOIN (\n" +
                "\tSELECT lh.bot_id,CONCAT(SEC_TO_TIME(SUM(totaluptime))) AS totaluptime\n" +
                "\tFROM  (\n" +
                "\t\tSELECT bot_id, job_id, seq_no, TIMESTAMPDIFF(SECOND, MIN(run_date), MAX(run_date)) AS totaluptime\n" +
                "\t\tFROM logging_history\n" +
                "\t\tWHERE job_id <> '000-0' AND step_id <> 'WAIT' AND run_date BETWEEN :startDate AND :endDate\n" +
                "\t\tGROUP BY bot_id, job_id, seq_no\n" +
                "\t) lh\n" +
                "\tGROUP BY bot_id\n" +
                ") lh_uptime ON lh.bot_id = lh_uptime.bot_id \n" +
                "WHERE lh.run_date BETWEEN :startDate AND :endDate\n" +
                "GROUP BY bot_id";

        Query nativeQuery = em.createNativeQuery(sql);

        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<LoggingHistoryStaticBotDTO> result = jpaResultMapper.list(nativeQuery, LoggingHistoryStaticBotDTO.class);

        em.close();

        return result;
    }

    // 대시보드 - 업무
    public List<DashboardJobDTO> getLoggingStaticJobAll(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql ="SELECT lh.bot_id\n" +
                "\t, bm.bot_name\n" +
                "\t, lh_current_raw.job_name\n" +
                "\t, lh_current_raw.step_name\n" +
                "\t, CAST(SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) AS INT) AS count_total\n" +
                "\t, ROUND(SUM(CASE lh.code_id_status WHEN 4 THEN 1 ELSE 0 END) / SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) * 100, 2) AS rate_success\n" +
                "\t, CASE WHEN lh_current_raw.code_id_status = 3 THEN '가동중인 봇 없음' WHEN DATE_SUB(NOW(), INTERVAL 1 HOUR) > lh_current_raw.run_date THEN CONCAT(lh_current_raw.bot_id,' - ','최신작업 오류') ELSE CONCAT(lh_current_raw.bot_id,' - ',lh_current_raw.step_name) END AS status_progress\n" +
                "\t, DATE_FORMAT(lh_current_raw.run_date, '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "\t, lh_uptime.totaluptime\n" +
                "\t, lh_uptime.avgtime\n" +
                "FROM logging_history AS lh\n" +
                "LEFT JOIN (\n" +
                "\tSELECT a.code_id_status \n" +
                "\t\t, c.job_id\n" +
                "\t\t, d.step_id\n" +
                "\t\t, c.job_name\n" +
                "\t\t, d.step_name\n" +
                "\t\t, a.run_date\n" +
                "\t\t, a.bot_id\n" +
                "\tFROM logging_history AS a\n" +
                "\tINNER JOIN (SELECT bot_id, job_id, MAX(run_date) AS cdate FROM logging_history WHERE job_id <> '000-0' GROUP BY bot_id, job_id) AS b ON a.bot_id = b.bot_id AND a.job_id = b.job_id AND a.run_date = b.cdate\n" +
                "\tINNER JOIN job_master AS c ON a.job_id = c.job_id\n" +
                "\tINNER JOIN step_master AS d ON a.job_id = d.job_id AND a.step_id = d.step_id\n" +
                "\tWHERE a.job_id <> '000-0'\n" +
                "\tGROUP BY bot_id, job_id\n" +
                ") lh_current_raw ON lh.bot_id = lh_current_raw.bot_id AND lh.job_id = lh_current_raw.job_id\n" +
                "INNER JOIN bot_master AS bm ON lh.bot_id = bm.bot_id\n" +
                "LEFT JOIN (\n" +
                "\tSELECT lh.job_id" +
                "\t\t, SEC_TO_TIME(SUM(totaluptime)) AS totaluptime\n" +
                "\t\t, SEC_TO_TIME(SUM(CASE WHEN lh.code_id_status = 4 THEN totaluptime ELSE 0 END) / SUM(CASE WHEN lh.code_id_status = 4 THEN 1 ELSE 0 END)) AS avgtime\n" +
                "\tFROM  (\n" +
                "\t\tSELECT bot_id" +
                "\t\t\t, job_id" +
                "\t\t\t, seq_no" +
                "\t\t\t, TIMESTAMPDIFF(SECOND, MIN(run_date), MAX(run_date)) AS totaluptime\n" +
                "\t\t\t, MAX(code_id_status) AS code_id_status\n" +
                "\t\tFROM logging_history\n" +
                "\t\tWHERE job_id <> '000-0'\n" +
                "\t\tGROUP BY bot_id, job_id, seq_no\n" +
                "\t) lh\n" +
                "\tGROUP BY job_id\n" +
                ") lh_uptime ON lh.job_id = lh_uptime.job_id\n" +
                "WHERE lh.job_id <> '000-0'\n" +
                "GROUP BY lh.job_id";

        sql = "SELECT lh.job_name\n" +
                "\t, lh_current_raw.bot_id\n" +
                "\t, lh_current_raw.step_name\n" +
                "\t, lh.count_total\n" +
                "\t, lh.rate_success\n" +
                "\t, lh_uptime.totaluptime\n" +
                "\t, lh_uptime.avgtime\n" +
                "\t, IFNULL((CASE WHEN lh_current_raw.code_id_status = 3 THEN '가동중인 봇 없음' WHEN DATE_SUB(NOW(), INTERVAL 1 HOUR) > lh_current_raw.run_date THEN CONCAT(lh_current_raw.bot_id,' - ','최신작업 오류') ELSE CONCAT(lh_current_raw.bot_id,' - ',lh_current_raw.step_name) end),'가동중인 봇 없음') AS status_progress\n" +
                "\t, DATE_FORMAT(lh_current_raw.run_date, '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "FROM (\n" +
                "\tSELECT \n" +
                "\t\tlh.job_id\n" +
                "\t\t, lh.bot_id\n" +
                "\t\t, jm.job_name\n" +
                "\t\t, CAST(SUM(CASE step_id WHEN 999 THEN 1 ELSE 0 END) AS INT) AS count_total\n" +
                "\t\t, IFNULL(ROUND(SUM(CASE lh.code_id_status WHEN 4 THEN 1 ELSE 0 END) / SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) * 100, 2),0) AS rate_success\n" +
                "\tFROM job_master AS jm\n" +
                "\tINNER JOIN logging_history AS lh ON lh.job_id = jm.job_id\n" +
                "\tWHERE jm.job_id <> '000-0'" +
                "\t\tAND lh.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY lh.job_id\n" +
                ") lh\n" +
                "LEFT OUTER JOIN (\n" +
                "\tSELECT a.code_id_status \n" +
                "\t\t, c.job_id\n" +
                "\t\t, d.step_id\n" +
                "\t\t, c.job_name\n" +
                "\t\t, d.step_name\n" +
                "\t\t, a.run_date\n" +
                "\t\t, a.bot_id\n" +
                "\tFROM logging_history AS a\n" +
                "\tINNER JOIN (SELECT job_id, MAX(run_date) AS cdate FROM logging_history WHERE job_id <> '000-0' AND run_date BETWEEN :startDate AND :endDate GROUP BY job_id) AS b ON a.job_id = b.job_id AND a.run_date = b.cdate\n" +
                "\tINNER JOIN job_master AS c ON a.job_id = c.job_id\n" +
                "\tINNER JOIN step_master AS d ON a.job_id = d.job_id AND a.step_id = d.step_id\n" +
                "\tWHERE a.job_id <> '000-0'\n" +
                "\tAND a.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY job_id\n" +
                ") lh_current_raw ON lh.job_id = lh_current_raw.job_id\n" +
                "LEFT OUTER JOIN (\n" +
                "\tSELECT lh.job_id\n" +
                "\t\t, CONCAT(SEC_TO_TIME(SUM(totaluptime))) AS totaluptime\n" +
                "\t\t, SEC_TO_TIME(SUM(CASE WHEN lh.code_id_status = 4 THEN totaluptime ELSE 0 END) / SUM(CASE WHEN lh.code_id_status = 4 THEN 1 ELSE 0 END)) AS avgtime\n" +
                "\tFROM  (\n" +
                "\t\tSELECT bot_id\n" +
                "\t\t\t, job_id\n" +
                "\t\t\t, seq_no\n" +
                "\t\t\t, TIMESTAMPDIFF(SECOND, MIN(run_date), MAX(run_date)) AS totaluptime\n" +
                "\t\t\t, MAX(code_id_status) AS code_id_status\n" +
                "\t\tFROM logging_history\n" +
                "\t\tWHERE job_id <> '000-0' AND step_id <> 'WAIT'\n" +
                "\t\tAND run_date BETWEEN :startDate AND :endDate\n" +
                "\t\tGROUP BY bot_id, job_id, seq_no\n" +
                "\t) lh\n" +
                "\tGROUP BY job_id\n" +
                ") lh_uptime ON lh.job_id = lh_uptime.job_id";

        Query nativeQuery = em.createNativeQuery(sql);

        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DashboardJobDTO> result = jpaResultMapper.list(nativeQuery, DashboardJobDTO.class);

        em.close();

        return result;
    }

    // 업무 별 통계
    public List<LoggingHistoryStaticJobDTO> getLoggingStaticJob(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql ="SELECT lh.bot_id\n" +
                "\t, bm.bot_name\n" +
                "\t, lh_current_raw.job_name\n" +
                "\t, lh_current_raw.step_name\n" +
                "\t, CAST(SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) AS INT) AS count_total\n" +
                "\t, CONCAT(ROUND(SUM(CASE lh.code_id_status WHEN 4 THEN 1 ELSE 0 END) / SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) * 100, 2),' %') AS rate_success\n" +
                "\t, CASE WHEN lh_current_raw.code_id_status = 3 THEN '가동중인 봇 없음' WHEN DATE_SUB(NOW(), INTERVAL 1 HOUR) > lh_current_raw.run_date THEN CONCAT(lh_current_raw.bot_id,' - ','최신작업 오류') ELSE CONCAT(lh_current_raw.bot_id,' - ',lh_current_raw.step_name) END AS status_progress\n" +
                "\t, DATE_FORMAT(lh_current_raw.run_date, '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "\t, lh_uptime.totaluptime\n" +
                "\t, lh_uptime.avgtime\n" +
                "FROM logging_history AS lh\n" +
                "LEFT JOIN (\n" +
                "\tSELECT a.code_id_status \n" +
                "\t\t, c.job_id\n" +
                "\t\t, d.step_id\n" +
                "\t\t, c.job_name\n" +
                "\t\t, d.step_name\n" +
                "\t\t, a.run_date\n" +
                "\t\t, a.bot_id\n" +
                "\tFROM logging_history AS a\n" +
                "\tINNER JOIN (SELECT bot_id, job_id, MAX(run_date) AS cdate FROM logging_history WHERE job_id <> '000-0' AND run_date BETWEEN :startDate AND :endDate GROUP BY bot_id, job_id) AS b ON a.bot_id = b.bot_id AND a.job_id = b.job_id AND a.run_date = b.cdate\n" +
                "\tINNER JOIN job_master AS c ON a.job_id = c.job_id\n" +
                "\tINNER JOIN step_master AS d ON a.job_id = d.job_id AND a.step_id = d.step_id\n" +
                "\tWHERE a.run_date BETWEEN :startDate AND :endDate\n" +
                "\t\tAND a.job_id <> '000-0'\n" +
                "\tGROUP BY bot_id, job_id\n" +
                ") lh_current_raw ON lh.bot_id = lh_current_raw.bot_id AND lh.job_id = lh_current_raw.job_id\n" +
                "INNER JOIN bot_master AS bm ON lh.bot_id = bm.bot_id\n" +
                "LEFT JOIN (\n" +
                "\tSELECT lh.job_id" +
                "\t\t, SEC_TO_TIME(SUM(totaluptime)) AS totaluptime\n" +
                "\t\t, SEC_TO_TIME(SUM(CASE WHEN lh.code_id_status = 4 THEN totaluptime ELSE 0 END) / SUM(CASE WHEN lh.code_id_status = 4 THEN 1 ELSE 0 END)) AS avgtime\n" +
                "\tFROM  (\n" +
                "\t\tSELECT bot_id" +
                "\t\t\t, job_id" +
                "\t\t\t, seq_no" +
                "\t\t\t, TIMESTAMPDIFF(SECOND, MIN(run_date), MAX(run_date)) AS totaluptime\n" +
                "\t\t\t, MAX(code_id_status) AS code_id_status\n" +
                "\t\tFROM logging_history\n" +
                "\t\tWHERE job_id <> '000-0'\n" +
                "\t\t\tAND run_date BETWEEN :startDate AND :endDate\n" +
                "\t\tGROUP BY bot_id, job_id, seq_no\n" +
                "\t) lh\n" +
                "\tGROUP BY job_id\n" +
                ") lh_uptime ON lh.job_id = lh_uptime.job_id\n" +
                "WHERE lh.run_date BETWEEN :startDate AND :endDate\n" +
                "\tAND lh.job_id <> '000-0'\n" +
                "GROUP BY lh.job_id";

        sql = "SELECT lh.job_name\n" +
                "\t, lh_current_raw.bot_id\n" +
                "\t, lh_current_raw.step_name\n" +
                "\t, lh.count_total\n" +
                "\t, lh.rate_success\n" +
                "\t, lh_uptime.totaluptime\n" +
                "\t, lh_uptime.avgtime\n" +
                "\t, IFNULL((CASE WHEN lh_current_raw.code_id_status = 3 THEN '가동중인 봇 없음' WHEN DATE_SUB(NOW(), INTERVAL 1 HOUR) > lh_current_raw.run_date THEN CONCAT(lh_current_raw.bot_id,' - ','최신작업 오류') ELSE CONCAT(lh_current_raw.bot_id,' - ',lh_current_raw.step_name) end),'가동중인 봇 없음') AS status_progress\n" +
                "\t, DATE_FORMAT(lh_current_raw.run_date, '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "FROM (\n" +
                "\tSELECT \n" +
                "\t\tlh.job_id\n" +
                "\t\t, lh.bot_id\n" +
                "\t\t, jm.job_name\n" +
                "\t\t, CAST(SUM(CASE step_id WHEN 999 THEN 1 ELSE 0 END) AS INT) AS count_total\n" +
                "\t\t, CONCAT(ROUND(SUM(CASE lh.code_id_status WHEN 4 THEN 1 ELSE 0 END) / SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) * 100, 2),' %') AS rate_success\n" +
                "\tFROM job_master AS jm\n" +
                "\tINNER JOIN logging_history AS lh ON lh.job_id = jm.job_id\n" +
                "\tWHERE jm.job_id <> '000-0'\n" +
                "\t\tAND lh.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY lh.job_id\n" +
                ") lh\n" +
                "LEFT JOIN (\n" +
                "\tSELECT a.code_id_status \n" +
                "\t\t, c.job_id\n" +
                "\t\t, d.step_id\n" +
                "\t\t, c.job_name\n" +
                "\t\t, d.step_name\n" +
                "\t\t, a.run_date\n" +
                "\t\t, a.bot_id\n" +
                "\tFROM logging_history AS a\n" +
                "\tINNER JOIN (SELECT job_id, MAX(run_date) AS cdate FROM logging_history WHERE job_id <> '000-0' AND run_date BETWEEN :startDate AND :endDate GROUP BY job_id) AS b ON a.job_id = b.job_id AND a.run_date = b.cdate\n" +
                "\tINNER JOIN job_master AS c ON a.job_id = c.job_id\n" +
                "\tINNER JOIN step_master AS d ON a.job_id = d.job_id AND a.step_id = d.step_id\n" +
                "\tWHERE a.job_id <> '000-0'\n" +
                "\tAND a.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY job_id\n" +
                ") lh_current_raw ON lh.job_id = lh_current_raw.job_id\n" +
                "LEFT JOIN (\n" +
                "\tSELECT lh.job_id\n" +
                "\t\t, CONCAT(SEC_TO_TIME(SUM(totaluptime))) AS totaluptime\n" +
                "\t\t, SEC_TO_TIME(SUM(CASE WHEN lh.code_id_status = 4 THEN totaluptime ELSE 0 END) / SUM(CASE WHEN lh.code_id_status = 4 THEN 1 ELSE 0 END)) AS avgtime\n" +
                "\tFROM  (\n" +
                "\t\tSELECT bot_id\n" +
                "\t\t\t, job_id\n" +
                "\t\t\t, seq_no\n" +
                "\t\t\t, TIMESTAMPDIFF(SECOND, MIN(run_date), MAX(run_date)) AS totaluptime\n" +
                "\t\t\t, MAX(code_id_status) AS code_id_status\n" +
                "\t\tFROM logging_history\n" +
                "\t\tWHERE job_id <> '000-0' AND step_id <> 'WAIT'\n" +
                "\t\tAND run_date BETWEEN :startDate AND :endDate\n" +
                "\t\tGROUP BY bot_id, job_id, seq_no\n" +
                "\t) lh\n" +
                "\tGROUP BY job_id\n" +
                ") lh_uptime ON lh.job_id = lh_uptime.job_id";

        Query nativeQuery = em.createNativeQuery(sql);

        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<LoggingHistoryStaticJobDTO> result = jpaResultMapper.list(nativeQuery, LoggingHistoryStaticJobDTO.class);

        em.close();

        return result;
    }


}
