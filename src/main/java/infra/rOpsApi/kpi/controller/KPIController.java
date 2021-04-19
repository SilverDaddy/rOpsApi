package infra.rOpsApi.kpi.controller;

import infra.rOpsApi.kpi.model.KPIChartDTO;
import infra.rOpsApi.kpi.model.KPICountDTO;
import infra.rOpsApi.kpi.repository.KPIRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kpi")
public class KPIController {
    @Autowired
    private KPIRepository kpiRepository;

    @GetMapping("/count")
    public List<KPICountDTO> getCounts(@Param("searchType") String searchType, @Param("jobs") String jobs
            , @Param("startDate") String startDate, @Param("endDate") String endDate
            , @Param("quarter") Integer quarter, @Param("year") Integer year) {
        List<KPICountDTO> resultList = new ArrayList<KPICountDTO>();
        if (searchType.equals("DAILY")) {
            LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            resultList = kpiRepository.getCountsDaily(start_date, end_date);
        } else if (searchType.equals("WEEKLY")) {
            System.out.println("quarter : " + quarter);
            resultList = kpiRepository.getCountsWeekly(year, quarter);
        } else if (searchType.equals("MONTHLY")) {
            resultList = kpiRepository.getCountsMonthly(year);
        }

        return resultList;
    }

    @GetMapping("/chartDataByTotal")
    public JSONObject getChartDataByTotal(@Param("searchType") String searchType, @Param("jobs") String jobs
            , @Param("startDate") String startDate, @Param("endDate") String endDate
            , @Param("quarter") Integer quarter, @Param("year") Integer year) {
        JSONObject jsonResult = new JSONObject();
        List<KPIChartDTO> resultList = new ArrayList<KPIChartDTO>();
        if (searchType.equals("DAILY")) {
            LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            resultList = kpiRepository.getChartDataByTotalDaily(start_date, end_date);
        } else if (searchType.equals("WEEKLY")) {
            System.out.println("quarter : " + quarter);
            resultList = kpiRepository.getChartDataByTotalWeekly(year, quarter);
        } else if (searchType.equals("MONTHLY")) {
            resultList = kpiRepository.getChartDataByTotalMonthly(year);
        }

        List<String> chart_axis = new ArrayList<String>();
        List<BigDecimal> working_time = new ArrayList<BigDecimal>();
        for (KPIChartDTO dto: resultList) {
            chart_axis.add(dto.getChart_axis());
            working_time.add(dto.getWorking_time());
        }
        jsonResult.put("data", working_time);
        jsonResult.put("labels", chart_axis);

        return jsonResult;
    }

    @GetMapping("/chartDataByJob")
    public JSONObject chartDataByJob(@Param("searchType") String searchType, @Param("jobs") String jobs
            , @Param("startDate") String startDate, @Param("endDate") String endDate
            , @Param("quarter") Integer quarter, @Param("year") Integer year) {
        JSONObject jsonResult = new JSONObject();
        List<KPIChartDTO> resultList = new ArrayList<KPIChartDTO>();
        if (searchType.equals("DAILY")) {
            LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            resultList = kpiRepository.getChartDataByJobDaily(start_date, end_date);
        } else if (searchType.equals("WEEKLY")) {
            System.out.println("quarter : " + quarter);
            resultList = kpiRepository.getChartDataByJobWeekly(year, quarter);
        } else if (searchType.equals("MONTHLY")) {
            resultList = kpiRepository.getChartDataByJobMonthly(year);
        }

        List<String> chart_axis = new ArrayList<String>();
        List<BigDecimal> working_time = new ArrayList<BigDecimal>();
        for (KPIChartDTO dto: resultList) {
            chart_axis.add(dto.getChart_axis());
            working_time.add(dto.getWorking_time());
        }
        jsonResult.put("data", working_time);
        jsonResult.put("labels", chart_axis);

        return jsonResult;
    }
}