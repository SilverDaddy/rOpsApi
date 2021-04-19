package infra.rOpsApi.dailyReport.repository;

import infra.rOpsApi.dailyReport.model.DailyReportBotDTO;
import infra.rOpsApi.dailyReport.model.DailyReportCountDTO;
import infra.rOpsApi.dailyReport.model.DailyReportJobDTO;
import infra.rOpsApi.dailyReport.model.DailyReportUtilizationDTO;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DailyReportRepository {
    @PersistenceContext
    private EntityManager em;

    public List<DailyReportCountDTO> getCounts(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql = "SELECT COUNT(*) AS count_total\n" +
                "\t, IFNULL(SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END),0) AS count_success\n" +
                "\t, IFNULL(SUM(CASE WHEN code_id_status = 5 THEN 1 ELSE 0 END),0) AS count_fail\n" +
                "\t, IFNULL(ROUND(SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) / COUNT(*) * 100, 2),0) AS rate_success\n" +
                "FROM logging_history AS lh\n" +
                "WHERE lh.job_id <> '000-0' AND lh.step_id = '999'" +
                "\tAND lh.run_date BETWEEN :startDate AND :endDate";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DailyReportCountDTO> result = jpaResultMapper.list(nativeQuery, DailyReportCountDTO.class);

        em.close();

        return result;
    }

    public List<DailyReportBotDTO> getBots(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql ="SELECT lh.bot_id\n" +
                "\t, CAST(SUM(CASE lh.step_id WHEN 999 THEN 1 ELSE 0 END) AS INT) AS count_total\n" +
                "\t, lh_uptime.totaluptime\n" +
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
        List<DailyReportBotDTO> result = jpaResultMapper.list(nativeQuery, DailyReportBotDTO.class);

        em.close();

        return result;
    }

    public List<DailyReportUtilizationDTO> getBotsUtilization(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql = "SELECT lh.bot_id\n" +
                "\t, IFNULL(ROUND(lh_uptime.totaluptime / lh_uptime_total.totaluptime * 100, 2),0) AS rate_utilization \n" +
                "FROM logging_history lh\n" +
                "INNER JOIN (\n" +
                "\tSELECT lh_uptime.bot_id\n" +
                "\t\t, SUM(totaluptime) AS totaluptime\t\n" +
                "\tFROM (\n" +
                "\t\tSELECT lh.bot_id\n" +
                "\t\t\t, TIMESTAMPDIFF(MINUTE , MIN(run_date), MAX(run_date)) AS totaluptime\n" +
                "\t\tFROM logging_history AS lh\n" +
                "\t\tWHERE lh.job_id <> '000-0' AND lh.step_id <> 'WAIT'\n" +
                "\t\t\tAND run_date BETWEEN :startDate AND :endDate\n" +
                "\t\tGROUP BY bot_id, job_id, seq_no\n" +
                "\t) lh_uptime\n" +
                "\tGROUP BY bot_id\n" +
                ") lh_uptime ON lh.bot_id = lh_uptime.bot_id\n" +
                "LEFT OUTER JOIN (\n" +
                "\tSELECT bot_id\n" +
                "\t\t,SUM(totaluptime) AS totaluptime\t\n" +
                "\tFROM (\n" +
                "\t\tSELECT bot_id\n" +
                "\t\t\t,TIMESTAMPDIFF(MINUTE , MIN(run_date), MAX(run_date)) AS totaluptime\n" +
                "\t\tFROM logging_history AS lh\n" +
                "\t\tWHERE lh.job_id <> '000-0' AND lh.step_id <> 'WAIT'\n" +
                "\t\t\tAND run_date BETWEEN :startDate AND :endDate\n" +
                "\t\tGROUP BY bot_id, job_id, seq_no\n" +
                "\t) lh_uptime_total\n" +
                ") lh_uptime_total ON lh.bot_id = lh_uptime.bot_id\n" +
                "WHERE run_date BETWEEN :startDate AND :endDate\n" +
                "GROUP BY bot_id";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DailyReportUtilizationDTO> result = jpaResultMapper.list(nativeQuery, DailyReportUtilizationDTO.class);

        em.close();

        return result;
    }

    public List<DailyReportJobDTO> getJobs(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql = "SELECT lh.job_name\n" +
                "\t, lh.count_total\n" +
                "\t, lh.rate_success\n" +
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
                "\tWHERE jm.job_id <> '000-0'\n" +
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
        List<DailyReportJobDTO> result = jpaResultMapper.list(nativeQuery, DailyReportJobDTO.class);

        em.close();

        return result;
    }
}
