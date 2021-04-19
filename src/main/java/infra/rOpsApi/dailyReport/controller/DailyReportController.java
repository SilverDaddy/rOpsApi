package infra.rOpsApi.dailyReport.controller;

import infra.rOpsApi.dailyReport.model.DailyReportBotDTO;
import infra.rOpsApi.dailyReport.model.DailyReportCountDTO;
import infra.rOpsApi.dailyReport.model.DailyReportJobDTO;
import infra.rOpsApi.dailyReport.model.DailyReportUtilizationDTO;
import infra.rOpsApi.dailyReport.repository.DailyReportRepository;
import infra.rOpsApi.dataChart.model.DataChartDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dailyReport")
public class DailyReportController {
    @Autowired
    private DailyReportRepository dailyReportRepository;

    @GetMapping("/count")
    public List<DailyReportCountDTO> getCounts(@Param("startDate") String startDate, @Param("endDate") String endDate) {
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return dailyReportRepository.getCounts(start_date, end_date);
    }

    @GetMapping("/bot")
    public List<DailyReportBotDTO> getBots(@Param("startDate") String startDate, @Param("endDate") String endDate) {
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return dailyReportRepository.getBots(start_date, end_date);
    }

    @GetMapping("/bot/utilization")
    public JSONObject getBotsUtilization(@Param("startDate") String startDate, @Param("endDate") String endDate) {
        JSONObject jsonResult = new JSONObject();
        List<String> bot_id = new ArrayList<String>();
        List<BigDecimal> rate_utilization = new ArrayList<BigDecimal>();

        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<DailyReportUtilizationDTO> data = dailyReportRepository.getBotsUtilization(start_date, end_date);
        for (DailyReportUtilizationDTO dto: data) {
            bot_id.add(dto.getBot_id());
            rate_utilization.add(dto.getRate_utilization());
        }

        jsonResult.put("data", rate_utilization);
        jsonResult.put("labels", bot_id);

        return jsonResult;
    }

    @GetMapping("/job")
    public List<DailyReportJobDTO> getJobs(@Param("startDate") String startDate, @Param("endDate") String endDate) {
        LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return dailyReportRepository.getJobs(start_date, end_date);
    }
}