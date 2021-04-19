package infra.rOpsApi.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import infra.rOpsApi.aa.controller.ScheduleController;
import infra.rOpsApi.dailyPlan.controller.DailyPlanController;
import infra.rOpsApi.dailyPlan.model.DailyPlan;
import infra.rOpsApi.dailyPlan.model.DailyPlanDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

@Component
public class Batch_DB {
    @Autowired
    private ScheduleController scheduleController;
    @Autowired
    private DailyPlanController dailyPlanController;

    // 밤 12시 1분에 오늘 돌아야 할 스케줄 업무 가져와서 DB에 INSERT
    @Scheduled(cron = "0 1 0 * * *")
    public void DB_INSERT_dailyPlan() throws JsonProcessingException, ParseException {
        ArrayList<Object> jsonResult = scheduleController.getSchedules();
        Gson gson = new Gson();
        JsonObject jsonObjectAlt = new JsonObject();
        String json = "";
        String scheduleName = "";
        String zonedNextRunDateTime = "";
        String korTimeString = "";
        String fileName = "";
        String status = "";

        Instant timestamp;
        ZonedDateTime korTimeZone;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();
        String today = format.format(currentTime);
        Date toDate = format.parse(today);
        Date compareDate;
        int compare;
        DailyPlanDto param = new DailyPlanDto();
        for (Object vo : jsonResult) {
            json = gson.toJson(vo);
            jsonObjectAlt = JsonParser.parseString(json).getAsJsonObject();

            status = jsonObjectAlt.get("status").getAsString();
            if (!status.equals("ACTIVE")) continue; // 스케줄 ACTIVE 상태인것만 사용
            fileName = jsonObjectAlt.get("fileName").getAsString();
            if (fileName.contains("HealthCheck") || fileName.contains("헬스체크")) continue; // 스케줄 헬스체크 제외
            if (fileName.contains("WORKLOAD_Manager")) continue; // WL 매니저 작업 제외
            scheduleName = jsonObjectAlt.get("name").getAsString();
            zonedNextRunDateTime = jsonObjectAlt.get("zonedNextRunDateTime").getAsString();

            timestamp = Instant.parse(zonedNextRunDateTime);
            korTimeZone = timestamp.atZone(ZoneId.of("Asia/Seoul"));
            korTimeString = korTimeZone.format(formatter);
            compareDate = format.parse(korTimeString);
            compare = compareDate.compareTo(toDate);
            if (compare == 0) {
                /*System.out.println("오늘 해야해!!!");
                System.out.println("scheduleName : " + scheduleName);
                System.out.println("zonedNextRunDateTime : " + zonedNextRunDateTime);
                System.out.println("korTimeString : " + korTimeString);*/

                param.setSchedule_name(scheduleName);
                param.setSchedule_time(korTimeString);
                param.setSchedule_file(fileName);
                dailyPlanController.insertDailyPlan(param);
            } else {
                /*System.out.println("오늘 할꺼 아니야~~");
                System.out.println("scheduleName : " + scheduleName);
                System.out.println("zonedNextRunDateTime : " + zonedNextRunDateTime);
                System.out.println("korTimeString : " + korTimeString);*/
            }
        }
    }
}
