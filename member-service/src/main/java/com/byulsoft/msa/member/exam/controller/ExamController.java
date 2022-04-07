package com.byulsoft.msa.member.exam.controller;

import com.byulsoft.msa.kafka.KafkaUtil;
import com.byulsoft.msa.member.exam.model.Member;
import com.byulsoft.msa.member.exam.model.ResponseDto;
import com.byulsoft.msa.member.exam.feign.ExamFeign;
import com.byulsoft.msa.member.exam.service.ExamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@AllArgsConstructor
@Data
public class ExamController {

    private ExamFeign examFeign;
    private ExamService examService;

    @GetMapping("/api/member/exam/kafka")
    public ResponseEntity<String> examKafka(@Valid Member params) throws Exception{

        log.info("kafka Start : {}", params);

        ObjectMapper mapper = new ObjectMapper();
        KafkaUtil.send("exam.kafka" , mapper.writeValueAsString(params));

        return ResponseEntity.ok(params.toString());
    }

    @GetMapping("/api/member/exam/getFeign")
    public ResponseEntity<ResponseDto> getFeign(Member params) {

        log.info("getFeign Start : {}", params);

        ResponseDto result = examFeign.getMessage(params);

        log.info("getFeign End : {}", result);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/member/exam/setFeign")
    public ResponseEntity<ResponseDto> setFeign(@Valid @RequestBody Member params) {

        log.info("setFeign Start : {}", params);

        ResponseDto result = null;
        try {
             result = examFeign.setMessage(params);
        } catch (FeignException e) {
            e.printStackTrace();
            result = new ResponseDto();
            result.setMessage(e.getMessage());
        }


        log.info("setFeign End : {}", result);

        return ResponseEntity.ok(result);
    }

}
