package com.byulsoft.msa.member.exam.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

    private Integer id;

    @NotEmpty(message = "이름은 필수 입력사항입니다.")
    private String name;

    private String tel;
}
