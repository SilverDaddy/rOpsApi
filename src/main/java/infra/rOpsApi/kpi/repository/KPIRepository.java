package infra.rOpsApi.kpi.repository;

import infra.rOpsApi.kpi.model.KPIChartDTO;
import infra.rOpsApi.kpi.model.KPICountDTO;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class KPIRepository {
    @PersistenceContext
    private EntityManager em;

    public List<KPICountDTO> getCountsDaily(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql = "SELECT COUNT(lh.job_id) AS count_job\n" +
                "\t, SUM(lh.count_total) AS count_run\n" +
                "\t, CONCAT(FLOOR(SUM(lh.working_time)/1440),'d ',MOD(FLOOR(SUM(lh.working_time)/60),24),'h ', MOD(SUM(lh.working_time),60),'m') AS working_time\n" +
                "FROM (\n" +
                "\tSELECT lh.job_id\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) AS count_total\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time\n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND lh.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY lh.job_id\n" +
                ") AS lh;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPICountDTO> result = jpaResultMapper.list(nativeQuery, KPICountDTO.class);

        em.close();

        return result;
    }

    public List<KPICountDTO> getCountsWeekly(@Param("year") Integer year, @Param("quarter") Integer quarter){
        String sql = "SELECT COUNT(lh.job_id) AS count_job\n" +
                "\t, SUM(lh.count_total) AS count_run\n" +
                "\t, CONCAT(FLOOR(SUM(lh.working_time)/1440),'d ',MOD(FLOOR(SUM(lh.working_time)/60),24),'h ', MOD(SUM(lh.working_time),60),'m') AS working_time\n" +
                "FROM (\n" +
                "\tSELECT lh.job_id\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) AS count_total\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time\n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND YEAR(lh.run_date) = :year\n" +
                "\t\tAND QUARTER(lh.run_date) = :quarter\n" +
                "\tGROUP BY lh.job_id\n" +
                ") AS lh;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("year", year);
        nativeQuery.setParameter("quarter", quarter);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPICountDTO> result = jpaResultMapper.list(nativeQuery, KPICountDTO.class);

        em.close();

        return result;
    }

    public List<KPICountDTO> getCountsMonthly(@Param("year") Integer year){
        String sql = "SELECT COUNT(lh.job_id) AS count_job\n" +
                "\t, SUM(lh.count_total) AS count_run\n" +
                "\t, CONCAT(FLOOR(SUM(lh.working_time)/1440),'d ',MOD(FLOOR(SUM(lh.working_time)/60),24),'h ', MOD(SUM(lh.working_time),60),'m') AS working_time\n" +
                "FROM (\n" +
                "\tSELECT lh.job_id\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) AS count_total\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time\n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND YEAR(lh.run_date) = :year\n" +
                "\tGROUP BY lh.job_id\n" +
                ") AS lh;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("year", year);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPICountDTO> result = jpaResultMapper.list(nativeQuery, KPICountDTO.class);

        em.close();

        return result;
    }

    public List<KPIChartDTO> getChartDataByTotalDaily(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql = "SELECT DATE_FORMAT(lh.run_date, '%Y-%m-%d') AS chart_axis\n" +
                "\t, ROUND(SUM(lh.working_time) / 60, 1) AS working_time\n" +
                "FROM (\n" +
                "\tSELECT run_date\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time \n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND lh.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY lh.job_id, run_date\n" +
                ") AS lh\n" +
                "GROUP BY chart_axis\n" +
                "ORDER BY chart_axis;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPIChartDTO> result = jpaResultMapper.list(nativeQuery, KPIChartDTO.class);

        em.close();

        return result;
    }

    public List<KPIChartDTO> getChartDataByTotalWeekly(@Param("year") Integer year, @Param("quarter") Integer quarter){
        String sql = "SELECT CONCAT(DATE_FORMAT(DATE_SUB(lh.run_date, INTERVAL(DAYOFWEEK(lh.run_date)-1) DAY),'%m-%d'),'~',DATE_FORMAT(DATE_SUB(lh.run_date, INTERVAL(DAYOFWEEK(lh.run_date)-7) DAY),'%m-%d')) AS chart_axis\n" +
                "\t, ROUND(SUM(lh.working_time) / 60, 1) AS working_time\n" +
                "FROM (\n" +
                "\tSELECT lh.job_id\n" +
                "\t\t, run_date\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time \n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND YEAR(lh.run_date) = :year\n" +
                "\t\tAND QUARTER(lh.run_date) = :quarter\n" +
                "\tGROUP BY lh.job_id, run_date\n" +
                ") AS lh\n" +
                "GROUP BY chart_axis\n" +
                "ORDER BY chart_axis;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("year", year);
        nativeQuery.setParameter("quarter", quarter);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPIChartDTO> result = jpaResultMapper.list(nativeQuery, KPIChartDTO.class);

        em.close();

        return result;
    }

    public List<KPIChartDTO> getChartDataByTotalMonthly(@Param("year") Integer year){
        String sql = "SELECT DATE_FORMAT(lh.run_date, '%m월') AS chart_axis\n" +
                "\t, ROUND(SUM(lh.working_time) / 60, 1) AS working_time\n" +
                "FROM (\n" +
                "\tSELECT lh.job_id\n" +
                "\t\t, run_date\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time \n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND YEAR(lh.run_date) = :year\n" +
                "\tGROUP BY lh.job_id, run_date\n" +
                ") AS lh\n" +
                "GROUP BY chart_axis\n" +
                "ORDER BY chart_axis;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("year", year);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPIChartDTO> result = jpaResultMapper.list(nativeQuery, KPIChartDTO.class);

        em.close();

        return result;
    }

    public List<KPIChartDTO> getChartDataByJobDaily(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql = "SELECT lh.job_name AS chart_axis\n" +
                "\t, ROUND(SUM(lh.working_time) / 60, 1) AS working_time\n" +
                "FROM (\n" +
                "\tSELECT jm.job_name\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time \n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND lh.run_date BETWEEN :startDate AND :endDate\n" +
                "\tGROUP BY lh.job_id\n" +
                ") AS lh\n" +
                "GROUP BY job_name\n" +
                "ORDER BY working_time DESC;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPIChartDTO> result = jpaResultMapper.list(nativeQuery, KPIChartDTO.class);

        em.close();

        return result;
    }

    public List<KPIChartDTO> getChartDataByJobWeekly(@Param("year") Integer year, @Param("quarter") Integer quarter){
        String sql = "SELECT lh.job_name AS chart_axis\n" +
                "\t, ROUND(SUM(lh.working_time) / 60, 1) AS working_time\n" +
                "FROM (\n" +
                "\tSELECT jm.job_name\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time \n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND YEAR(lh.run_date) = :year\n" +
                "\t\tAND QUARTER(lh.run_date) = :quarter\n" +
                "\tGROUP BY lh.job_id\n" +
                ") AS lh\n" +
                "GROUP BY job_name\n" +
                "ORDER BY working_time DESC;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("year", year);
        nativeQuery.setParameter("quarter", quarter);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPIChartDTO> result = jpaResultMapper.list(nativeQuery, KPIChartDTO.class);

        em.close();

        return result;
    }

    public List<KPIChartDTO> getChartDataByJobMonthly(@Param("year") Integer year){
        String sql = "SELECT lh.job_name AS chart_axis\n" +
                "\t, ROUND(SUM(lh.working_time) / 60, 1) AS working_time\n" +
                "FROM (\n" +
                "\tSELECT jm.job_name\n" +
                "\t\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) * jm.working_time AS working_time \n" +
                "\tFROM logging_history AS lh\n" +
                "\tINNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND YEAR(lh.run_date) = :year\n" +
                "\tGROUP BY lh.job_id\n" +
                ") AS lh\n" +
                "GROUP BY job_name\n" +
                "ORDER BY working_time DESC;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("year", year);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<KPIChartDTO> result = jpaResultMapper.list(nativeQuery, KPIChartDTO.class);

        em.close();

        return result;
    }
}
