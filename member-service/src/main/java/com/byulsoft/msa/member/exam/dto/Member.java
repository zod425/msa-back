package com.byulsoft.msa.member.exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Member {

    private Integer id;

    @NotEmpty(message = "이름은 필수 입력사항입니다.")
    private String name;

    private String tel;
}
