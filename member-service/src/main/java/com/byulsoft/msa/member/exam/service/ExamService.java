package com.byulsoft.msa.member.exam.service;

import com.byulsoft.msa.member.exam.model.Member;
import com.byulsoft.msa.member.exam.model.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ExamService {
    void examKafka(Member params) throws JsonProcessingException;

    ResponseDto testFeign(Member params);

    ResponseDto testFeignError(Member params);
}
