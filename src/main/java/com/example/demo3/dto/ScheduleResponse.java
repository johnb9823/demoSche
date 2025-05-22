package com.example.demo3.dto;

import com.example.demo3.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {

    private Long id;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    public ScheduleResponse(Long id, String name, String content, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
    }

    //객체를 받는 생성자
//    public ScheduleResponse(Schedule schedule) {
//        this.id = schedule.getId();
//        this.name = schedule.getName();
//        this.content = schedule.getContent();
//        this.createdAt = schedule.getCreatedAt();
//    }

}
