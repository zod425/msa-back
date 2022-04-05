package com.byulsoft.msa.sms.exam.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Sms {

    @NotEmpty(message = "이름은 필수 입력사항입니다.")
    private String name;

    @NotEmpty(message = "전화번호는 필수 입력사항입니다.")
    private String tel;
}
