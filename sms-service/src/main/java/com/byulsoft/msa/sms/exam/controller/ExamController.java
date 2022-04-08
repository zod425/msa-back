package com.byulsoft.msa.sms.exam.controller;

import com.byulsoft.msa.sms.exam.dto.Sms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ExamController {


    @GetMapping("/api/sms/exam/getMessage")
    public ResponseEntity<Map<String, Object>> getMessage(@RequestParam Map<String, Object> params) throws Exception{

        log.info("getTicket Info : {}" , params.toString());

        Thread.sleep(1000 * 3);

        log.info("3초간 대기 후에 응답 처리");

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("message", "성공");
        returnMap.put("param", params);

        return ResponseEntity.ok(returnMap);
    }

    @PostMapping("/api/sms/exam/setMessage")
    public ResponseEntity<Sms> setMessage(@Valid @RequestBody Sms params) {

        log.info("setTicket Info : {}" , params.toString());

        return ResponseEntity.ok(params);
    }
}
