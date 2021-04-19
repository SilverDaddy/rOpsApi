package infra.rOpsApi.aa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import infra.rOpsApi.aa.common.Util;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Util util;

    @Value("${rpa.controlroom.url}")
    private String CR_URL;
    @Value("${rpa.controlroom.username}")
    private String CR_USERNAME;
    @Value("${rpa.controlroom.password}")
    private String CR_PASSWORD;

    @PostMapping("/v1/authentication")
    public String getAuthentication() throws JsonProcessingException {
        String jsonResult = "";
        String API_URL = CR_URL + "/v1/authentication";

        HttpHeaders header = new HttpHeaders();
        JSONObject params = new JSONObject();
        params.put("username", CR_USERNAME);
        params.put("password", CR_PASSWORD);
        HttpEntity<?> entity = new HttpEntity<>(params, header);

        ResponseEntity<?> resultMap = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);
        /*HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("statusCode", resultMap.getStatusCodeValue());
        result.put("header", resultMap.getHeaders());
        result.put("body", resultMap.getBody());*/

        ObjectMapper mapper = new ObjectMapper();
        jsonResult = mapper.writeValueAsString(resultMap.getBody());

        return jsonResult;
    }
}
