package infra.rOpsApi.dailyPlan.repository;

import infra.rOpsApi.dailyPlan.model.DoingDTO;
import infra.rOpsApi.dailyPlan.model.DoneDTO;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TodoListRepository {
    @PersistenceContext
    private EntityManager em;

    public List<DoingDTO> getDoing(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql = "SELECT jm.job_name\n" +
                "\t, dp.schedule_time\n" +
                "\t, DATE_FORMAT(lh.run_date, '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "FROM daily_plan AS dp\n" +
                "INNER JOIN job_master AS jm ON dp.schedule_file = jm.description\n" +
                "LEFT OUTER JOIN (\n" +
                "\tSELECT lh.job_id\n" +
                "\t\t, lh.run_date\n" +
                "\t\t, lh.code_id_status\n" +
                "\tFROM logging_history AS lh \n" +
                "\tWHERE lh.job_id <> '000-0' AND lh.step_id = '999'\n" +
                "\t\tAND lh.run_date BETWEEN :startDate AND :endDate\n" +
                ") AS lh ON lh.job_id = jm.job_id\n" +
                "WHERE dp.created_date BETWEEN :startDate AND :endDate\n" +
                "\tAND (lh.code_id_status IS NULL OR lh.code_id_status = 5)\n" +
                "ORDER BY dp.schedule_time ASC;";

        sql = "SELECT jm.job_name\n" +
                "\t, dp.schedule_time\n" +
                "\t, DATE_FORMAT(lh.run_date, '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "FROM daily_plan AS dp \n" +
                "INNER JOIN job_master AS jm ON dp.schedule_file = jm.description \n" +
                "LEFT OUTER JOIN (\n" +
                "\tSELECT lh2.job_id \n" +
                "\t\t, lh2.run_date\n" +
                "\t\t, lh2.code_id_status \n" +
                "\tFROM logging_history AS lh2\n" +
                "\tINNER JOIN (\n" +
                "\t\tSELECT lh.job_id\n" +
                "\t    \t, MAX(lh.run_date) AS run_date \n" +
                "\t    FROM logging_history AS lh \n" +
                "\t    WHERE lh.job_id <> '000-0' \n" +
                "\t        AND lh.step_id = '999'   \n" +
                "\t        AND lh.run_date BETWEEN :startDate AND :endDate\n" +
                "\t    GROUP BY lh.job_id \n" +
                "\t) AS lh ON lh2.job_id = lh.job_id AND lh2.run_date = lh.run_date\n" +
                "\tWHERE lh2.job_id <> '000-0' \n" +
                "\t        AND lh2.step_id = '999'   \n" +
                "\t        AND lh2.run_date BETWEEN :startDate AND :endDate\n" +
                ") AS lh ON lh.job_id = jm.job_id \n" +
                "WHERE dp.created_date BETWEEN :startDate AND :endDate\n" +
                "\t AND (lh.code_id_status IS NULL OR lh.code_id_status = 5) \n" +
                "ORDER BY dp.schedule_time ASC;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DoingDTO> result = jpaResultMapper.list(nativeQuery, DoingDTO.class);

        em.close();

        return result;
    }

    public List<DoneDTO> getDone(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate){
        String sql = "SELECT jm.job_name\n" +
                "\t, DATE_FORMAT(MAX(lh.run_date), '%Y-%m-%d %H:%i:%s') AS run_date\n" +
                "FROM daily_plan AS dp\n" +
                "INNER JOIN job_master AS jm ON dp.schedule_file = jm.description AND jm.job_id <> '000-0'\n" +
                "INNER JOIN logging_history AS lh ON lh.job_id = jm.job_id\n" +
                "WHERE lh.job_id <> '000-0' AND lh.step_id = '999' AND lh.code_id_status = 4\n" +
                "\tAND dp.created_date BETWEEN :startDate AND :endDate\n" +
                "\tAND lh.run_date BETWEEN :startDate AND :endDate\n" +
                "GROUP BY jm.job_name\n" +
                "ORDER BY lh.run_date ASC;";

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<DoneDTO> result = jpaResultMapper.list(nativeQuery, DoneDTO.class);

        em.close();

        return result;
    }
}
