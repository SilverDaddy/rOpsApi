package infra.rOpsApi.dataChart.repository;

import infra.rOpsApi.dataChart.model.DataChartDTO;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataChartRepository {
    @PersistenceContext
    private EntityManager em;

    public List<DataChartDTO> getDataChartDaily(@Param("jobs") List<String> jobs, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql ="SELECT lh.job_id\n" +
                "\t, jm.job_name\n" +
                "\t, DATE_FORMAT(lh.run_date, '%Y-%m-%d') AS date_axis\n" +
                "\t, COUNT(*) AS count_total\n" +
                "\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) AS count_success\n" +
                "\t, SUM(CASE WHEN code_id_status = 5 THEN 1 ELSE 0 END) AS count_fail\n" +
                "\t, IFNULL(ROUND(SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) / COUNT(*) * 100, 2),0) AS rate_success\n" +
                "FROM logging_history AS lh\n" +
                "INNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "WHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\tAND lh.job_id IN :jobs\n" +
                "\tAND lh.run_date BETWEEN :startDate AND :endDate\n" +
                "GROUP BY lh.job_id, date_axis\n" +
                "ORDER BY date_axis, lh.job_id;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("jobs", jobs);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DataChartDTO> result = jpaResultMapper.list(nativeQuery, DataChartDTO.class);

        em.close();

        return result;
    }

    public List<DataChartDTO> getDataChartWeekly(@Param("jobs") List<String> jobs, @Param("year") Integer year, @Param("quarter") Integer quarter){
        String sql ="SELECT lh.job_id\n" +
                "\t, jm.job_name\n" +
                "\t, CONCAT(DATE_FORMAT(DATE_SUB(lh.run_date, INTERVAL(DAYOFWEEK(lh.run_date)-1) DAY),'%m-%d'),'~',DATE_FORMAT(DATE_SUB(lh.run_date, INTERVAL(DAYOFWEEK(lh.run_date)-7) DAY),'%m-%d')) AS date_axis\n" +
                "\t, COUNT(*) AS count_total\n" +
                "\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) AS count_success\n" +
                "\t, SUM(CASE WHEN code_id_status = 5 THEN 1 ELSE 0 END) AS count_fail\n" +
                "\t, IFNULL(ROUND(SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) / COUNT(*) * 100, 2),0) AS rate_success\n" +
                "FROM logging_history AS lh\n" +
                "INNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "WHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\tAND lh.job_id IN :jobs\n" +
                "\tAND YEAR(lh.run_date) = :year\n" +
                "\tAND QUARTER(lh.run_date) = :quarter\n" +
                "GROUP BY lh.job_id, date_axis\n" +
                "ORDER BY date_axis, job_id;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("jobs", jobs);
        nativeQuery.setParameter("year", year);
        nativeQuery.setParameter("quarter", quarter);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DataChartDTO> result = jpaResultMapper.list(nativeQuery, DataChartDTO.class);

        em.close();

        return result;
    }

    public List<DataChartDTO> getDataChartMonthly(@Param("jobs") List<String> jobs, @Param("year") Integer year){
        String sql ="SELECT lh.job_id\n" +
                "\t, jm.job_name\n" +
                "\t, DATE_FORMAT(lh.run_date, '%m월') AS date_axis\n" +
                "\t, COUNT(*) AS count_total\n" +
                "\t, SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) AS count_success\n" +
                "\t, SUM(CASE WHEN code_id_status = 5 THEN 1 ELSE 0 END) AS count_fail\n" +
                "\t, IFNULL(ROUND(SUM(CASE WHEN code_id_status = 4 THEN 1 ELSE 0 END) / COUNT(*) * 100, 2),0) AS rate_success\n" +
                "FROM logging_history AS lh\n" +
                "INNER JOIN job_master AS jm ON lh.job_id  = jm.job_id \n" +
                "WHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\tAND lh.job_id IN :jobs\n" +
                "\tAND YEAR(lh.run_date) = :year\n" +
                "GROUP BY lh.job_id, date_axis\n" +
                "ORDER BY date_axis, job_id;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("jobs", jobs);
        nativeQuery.setParameter("year", year);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DataChartDTO> result = jpaResultMapper.list(nativeQuery, DataChartDTO.class);

        em.close();

        return result;
    }
}
