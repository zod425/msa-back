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

    private ExamService examService;

    @GetMapping("/api/member/exam/kafka")
    public ResponseEntity<String> examKafka(@Valid Member params) throws Exception{

        examService.examKafka(params);

        return ResponseEntity.ok(params.toString());
    }

    @GetMapping("/api/member/exam/testFeign")
    public ResponseEntity<ResponseDto> testFeign(Member params) {

        ResponseDto result = examService.testFeign(params);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/member/exam/testFeignError")
    public ResponseEntity<ResponseDto> testFeignError(@Valid @RequestBody Member params) {

        log.info("testFeignError Start : {}", params);

        ResponseDto result = examService.testFeignError(params);

        log.info("testFeignError End : {}", result);

        return ResponseEntity.ok(result);
    }

}
