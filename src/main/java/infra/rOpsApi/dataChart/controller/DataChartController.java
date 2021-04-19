package infra.rOpsApi.dataChart.controller;

import infra.rOpsApi.dataChart.model.DataChartDTO;
import infra.rOpsApi.dataChart.repository.DataChartRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/datachart")
public class DataChartController {
    @Autowired
    private DataChartRepository dataChartRepository;

    @GetMapping
    public JSONObject getDataChartByPeriod(@Param("searchType") String searchType, @Param("jobs") String jobs
            , @Param("startDate") String startDate, @Param("endDate") String endDate
            , @Param("quarter") Integer quarter, @Param("year") Integer year) {

        List<String> jobList = Arrays.asList(jobs.split(","));
        List<DataChartDTO> list = new ArrayList<DataChartDTO>();
        if (searchType.equals("DAILY")) {
            LocalDateTime start_date = LocalDateTime.parse(startDate+" 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime end_date = LocalDateTime.parse(endDate+" 23:59:59",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            list = dataChartRepository.getDataChartDaily(jobList, start_date, end_date);
        } else if (searchType.equals("WEEKLY")) {
            System.out.println("quarter : " + quarter);
            list = dataChartRepository.getDataChartWeekly(jobList, year, quarter);
        } else if (searchType.equals("MONTHLY")) {
            list = dataChartRepository.getDataChartMonthly(jobList, year);
        }

        JSONObject jsonResult = new JSONObject();
        List<String> x_axis = new ArrayList<String>();
        List<String> jobIds = new ArrayList<String>();
        List<String> data_successRate = new ArrayList<String>();
        List<String> data_totalCount = new ArrayList<String>();
        List<String> data_failCount = new ArrayList<String>();
        JSONObject chartData_successRate = new JSONObject();
        JSONObject chartData_totalCount = new JSONObject();
        JSONObject chartData_failCount = new JSONObject();
        JSONArray chartDataArray_successRate = new JSONArray();
        JSONArray chartDataArray_totalCount = new JSONArray();
        JSONArray chartDataArray_failCount = new JSONArray();

        // X축 데이터 생성 및 jobId 식별
        String date_axis = "";
        for (DataChartDTO dto: list) {
            date_axis = dto.getDate_axis();
            if (!x_axis.contains(date_axis)) {
                x_axis.add(date_axis);
            }

            if (!jobIds.contains(dto.getJob_id())) {
                jobIds.add(dto.getJob_id());
            }
        }

        String label = "";
        List<String> date_axis_temp = new ArrayList<String>();
        List<String> date_axis_insert = new ArrayList<String>();
        for (String jobid : jobIds) { // jobid 만큼 데이터 셋 만들꺼임
            data_successRate = new ArrayList<String>();
            data_totalCount = new ArrayList<String>();
            data_failCount = new ArrayList<String>();
            chartData_successRate = new JSONObject();
            chartData_totalCount = new JSONObject();
            chartData_failCount = new JSONObject();
            date_axis_temp = new ArrayList<String>();
            date_axis_insert = new ArrayList<String>();

            for (String x : x_axis) {
                for (DataChartDTO dto: list) {
                    if (jobid.equals(dto.getJob_id()) && x.equals(dto.getDate_axis())) {
                        date_axis_temp.add(dto.getDate_axis());
                    }
                }
            }

           for (DataChartDTO dto: list) {
                if (jobid.equals(dto.getJob_id())) {
                    label = dto.getJob_name();
                    data_successRate.add(dto.getRate_success().toString());
                    data_totalCount.add(dto.getCount_total().toString());
                    data_failCount.add(dto.getCount_fail().toString());
                    date_axis_insert.add(dto.getDate_axis()); // 데이터 추가한 x축
                } else {
                    // x축 데이터를 가지고 있지 않거나, 데이터를 추가하지 않은 x축 데이터는 0값 추가
                    if (!date_axis_temp.contains(dto.getDate_axis()) && !date_axis_insert.contains(dto.getDate_axis())) {
                        date_axis_insert.add(dto.getDate_axis());
                        data_successRate.add("0");
                        data_totalCount.add("0");
                        data_failCount.add("0");
                    }
                }
            }

            chartData_successRate.put("label", label);
            chartData_successRate.put("data",data_successRate);
            chartDataArray_successRate.add(chartData_successRate);

            chartData_totalCount.put("label", label);
            chartData_totalCount.put("data",data_totalCount);
            chartDataArray_totalCount.add(chartData_totalCount);

            chartData_failCount.put("label", label);
            chartData_failCount.put("data",data_failCount);
            chartDataArray_failCount.add(chartData_failCount);
        }

        jsonResult.put("successRate", chartDataArray_successRate);
        jsonResult.put("totalCount", chartDataArray_totalCount);
        jsonResult.put("failCount", chartDataArray_failCount);
        jsonResult.put("labels", x_axis);
        System.out.println("jsonResult : "+jsonResult);

        return jsonResult;
    }

}