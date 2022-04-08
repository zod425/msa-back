package com.byulsoft.msa.member.exam.service;

import com.byulsoft.msa.kafka.KafkaUtil;
import com.byulsoft.msa.member.exam.feign.ExamFeign;
import com.byulsoft.msa.member.exam.model.Member;
import com.byulsoft.msa.member.exam.model.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ExamServiceImpl implements ExamService {
    private ExamFeign examFeign;


    @Override
    public void examKafka(Member params) throws JsonProcessingException {
        log.info("kafka Start : {}", params);

        ObjectMapper mapper = new ObjectMapper();
        KafkaUtil.send("exam.kafka" , mapper.writeValueAsString(params));
    }

    @Override
    public ResponseDto testFeign(Member params) {
        log.info("testFeign Start : {}", params);

        ResponseDto result = examFeign.getMessage(params);

        log.info("testFeign End : {}", result);

        return result;
    }

    @Override
    public ResponseDto testFeignError(Member params) {
        ResponseDto result = null;
        try {
            result = examFeign.setMessage(params);
        } catch (FeignException e) {
            log.error("Feign 호출줄 오류", e);
            result = new ResponseDto();
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
