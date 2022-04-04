package com.byulsoft.msa.member.exam.controller;

import com.byulsoft.msa.kafka.KafkaUtil;
import com.byulsoft.msa.member.exam.feign.ExamFeign;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
public class ExamController {

    private ExamFeign examFeign;

    public ExamController(ExamFeign examFeign) {
        this.examFeign = examFeign;
    }

    @GetMapping("/api/exam/kafka")
    public ResponseEntity<String> examKafka(@RequestParam Map<String, Object> params) throws Exception{

        log.info("kafka Start : {}", params);

        ObjectMapper mapper = new ObjectMapper();
        KafkaUtil.send("exam.kafka" , mapper.writeValueAsString(params));

        return ResponseEntity.status(HttpStatus.OK).header(null).body(params.toString());
    }

    @GetMapping("/api/exam/getFeign")
    public ResponseEntity<Map<String, Object>> getFeign(@RequestParam Map<String, Object> params) {

        log.info("getFeign Start : {}", params);

        Map<String, Object> result = examFeign.getTicket(params);

        log.info("getFeign End : {}", result);

        return ResponseEntity.status(HttpStatus.OK).header(null).body(result);
    }

    @PostMapping("/api/exam/setFeign")
    public ResponseEntity<Map<String, Object>> setFeign(@RequestParam Map<String, Object> params) {

        log.info("setFeign Start : {}", params);

        Map<String, Object> result = examFeign.setTicket(params);

        log.info("setFeign End : {}", result);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).header(null).body(result);
    }
}
