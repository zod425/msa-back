package com.byulsoft.msa.member.exam.model;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseDto {

    private String message;

    private Map<String, Object> param;
}
