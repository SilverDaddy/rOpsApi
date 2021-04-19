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
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping
public class WLMController {
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

    @GetMapping("/wlm/queues")
    public ArrayList<Object> getQueues() throws JsonProcessingException {
        String API_URL = CR_URL + "/v1/wlm/queues";
        String authJSON = authController.getAuthentication();
        String token = util.getValueByKey(authJSON,"token");

        HttpHeaders header = new HttpHeaders();
        header.add("X-Authorization", token);
        HttpEntity<?> entity = new HttpEntity<>(header);
        ResponseEntity<?> resultList = restTemplate.exchange(API_URL, HttpMethod.GET, entity, List.class);

        ArrayList<Object> jsonResult = (ArrayList<Object>) resultList.getBody();
        System.out.println("jsonResult : " + jsonResult);

        return jsonResult;
    }

    @PostMapping("/wlm/queues/{queue_id}/workitems")
    public String insertWorkItem(@PathVariable("queue_id") String queue_id, @RequestBody Structure_common params) throws JsonProcessingException {
        String API_URL = CR_URL + "/v2/wlm/queues/"+queue_id+"/workitems";
        String authJSON = authController.getAuthentication();
        String token = util.getValueByKey(authJSON,"token");
        String resultString = "";
        ArrayList<Object> jsonResult = new ArrayList<>();

        try {
            HttpHeaders header = new HttpHeaders();
            header.add("X-Authorization", token);
            JSONObject jsonObject = new JSONObject();
            JSONObject data = new JSONObject();
            JSONObject data2 = new JSONObject();
            data.put("TASK", params.getTask());
            data.put("PARAM", params.getParam());
            data2.put("json", data);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(data2);

            jsonObject.put("workItems", jsonArray);

            HttpEntity<?> entity = new HttpEntity<>(jsonObject, header);
            ResponseEntity<?> resultList = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);
            resultString = resultList.getStatusCode().toString();
            System.out.println("getBody : " + resultList.getBody().toString());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            resultString = String.valueOf(e.getRawStatusCode())+ " " + e.getStatusText();
        } catch (Exception e) {
            resultString = "999 ExcpetionError";
        }
        return resultString;
    }

    @PostMapping("/wlm/queues/{queue_id}/workitems/list")
    public ArrayList<Object> getWorkItem(@PathVariable("queue_id") String queue_id) throws JsonProcessingException {
        String API_URL = CR_URL + "/v2/wlm/queues/"+queue_id+"/workitems/list";
        String authJSON = authController.getAuthentication();
        String token = util.getValueByKey(authJSON,"token");

        HttpHeaders header = new HttpHeaders();
        header.add("X-Authorization", token);
        JSONObject jsonObject = new JSONObject();
        JSONObject sort = new JSONObject();
        JSONArray sortArray = new JSONArray();
        JSONObject filter = new JSONObject();
        JSONArray filterArray = new JSONArray();
        JSONArray statusArray = new JSONArray();
        JSONObject page = new JSONObject();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDaysAgo = now.minusDays(1); // 1일 전

        sort.put("field","id");
        sort.put("direction","desc");
        sortArray.add(sort);

        // 상태 조건 - Complete
        filter.put("operator", "eq");
        filter.put("field", "status");
        filter.put("value", "COMPLETED");
        statusArray.add(filter);
        filter = new JSONObject();
        filter.put("operator", "gt");
        filter.put("field", "startTime");
        filter.put("value", oneDaysAgo.format(DateTimeFormatter.ISO_DATE_TIME)+"+09:00");
        statusArray.add(filter);

        filter = new JSONObject();
        filter.put("operator", "and");
        filter.put("operands", statusArray);
        filterArray.add(filter);

        // 상태 조건 - Active
        statusArray = new JSONArray();
        filter = new JSONObject();
        filter.put("operator", "eq");
        filter.put("field", "status");
        filter.put("value", "WORK_IN_PROGRESS");
        statusArray.add(filter);
        filter = new JSONObject();
        filter.put("operator", "eq");
        filter.put("field", "status");
        filter.put("value", "PUSH_IN_PROGRESS");
        statusArray.add(filter);
        filter = new JSONObject();
        filter.put("operator", "eq");
        filter.put("field", "status");
        filter.put("value", "PUSHED");
        statusArray.add(filter);
        filter = new JSONObject();
        filter.put("operator", "or");
        filter.put("operands", statusArray);
        filterArray.add(filter);

        // 상태 조건 - Ready to tun
        statusArray = new JSONArray();
        filter = new JSONObject();
        filter.put("operator", "eq");
        filter.put("field", "status");
        filter.put("value", "NEW");
        statusArray.add(filter);
        filter = new JSONObject();
        filter.put("operator", "eq");
        filter.put("field", "status");
        filter.put("value", "STAGED");
        statusArray.add(filter);
        filter = new JSONObject();
        filter.put("operator", "eq");
        filter.put("field", "status");
        filter.put("value", "QUEUED");
        statusArray.add(filter);
        filter = new JSONObject();
        filter.put("operator", "or");
        filter.put("operands", statusArray);
        filterArray.add(filter);

        /*// 시간 필터
        filter = new JSONObject();
        filter.put("operator", "gt");
        filter.put("field", "startTime");
        filter.put("value", oneDaysAgo.format(DateTimeFormatter.ISO_DATE_TIME)+"Z");
        filterArray.add(filter);*/

        filter = new JSONObject();
        filter.put("operator", "or");
        filter.put("operands", filterArray);

        page.put("length",200);
        page.put("offset",0);
        //filter = new JSONObject();
        jsonObject.put("sort", sortArray);
        jsonObject.put("filter", filter);
        jsonObject.put("page", page);
        System.out.println("jsonObject : " + jsonObject);

        HttpEntity<?> entity = new HttpEntity<>(jsonObject, header);
        ResponseEntity<Map> resultMap = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);
        ArrayList<Object> jsonResult = (ArrayList<Object>) resultMap.getBody().get("list");
        System.out.println("jsonResult : " + jsonResult);

        return jsonResult;
    }
}
