package infra.rOpsApi.aa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import infra.rOpsApi.aa.common.Util;
import infra.rOpsApi.aa.model.Structure_common;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ScheduleController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Util util;
    @Autowired
    private AuthController authController;

    @Value("${rpa.controlroom.url}")
    private String CR_URL;
    @Value("${rpa.controlroom.username}")
    private String CR_USERNAME;
    @Value("${rpa.controlroom.password}")
    private String CR_PASSWORD;

    @PostMapping("/schedule/automations/list")
    public ArrayList<Object> getSchedules() throws JsonProcessingException {
        String API_URL = CR_URL + "/v1/schedule/automations/list";
        String authJSON = authController.getAuthentication();
        String token = util.getValueByKey(authJSON,"token");

        HttpHeaders header = new HttpHeaders();
        header.add("X-Authorization", token);
        JSONObject jsonObject = new JSONObject();
        JSONObject sort = new JSONObject();
        JSONArray sortArray = new JSONArray();
        JSONObject filter = new JSONObject();
        JSONArray filterArray = new JSONArray();
        JSONObject page = new JSONObject();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDaysAgo = now.minusDays(1); // 1일 전

        sort.put("field","zonedNextRunDateTime");
        sort.put("direction","asc");
        sortArray.add(sort);

        // 상태 조건 - Complete

        page.put("length",200);
        page.put("offset",0);
        //filter = new JSONObject();
        jsonObject.put("sort", sortArray);
        jsonObject.put("filter", filter);
        jsonObject.put("page", page);

        HttpEntity<?> entity = new HttpEntity<>(jsonObject, header);
        ResponseEntity<Map> resultMap = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);
        ArrayList<Object> jsonResult = (ArrayList<Object>) resultMap.getBody().get("list");
        System.out.println("jsonResult : " + jsonResult);

        return jsonResult;
    }

    @PostMapping("/schedule/automations/list2")
    public void getSchedules2() {
        System.out.println("이주환 천재");
    }
}
