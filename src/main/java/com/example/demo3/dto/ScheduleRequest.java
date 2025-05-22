package com.example.demo3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ScheduleRequest {

    // Entity 클래스의 제약조건과 Dto의 제약조건의 검증시점이 다름. 그래서 또 적어줌.
    @NotBlank
    private String name;

    @NotEmpty
    private String content;

}
