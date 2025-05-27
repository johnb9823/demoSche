package com.example.demo3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id // 기본키(엔티티 어노테이션을 붙이고 아이디를 안 만들면 오류남)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 값을 자동으로 1씩 증가 시켜줌
    private Long id;

    @Column(nullable = false) // 이렇게 하면 DB에 NOT NULL 제약조건이 추가 됨
     //Java 코드 실행 시점에서 입력값 검증 (예: DTO로 받은 값 검사), @Valid 같이 써야 유효성 체크
    // NotBlank는 Null, "", " " 모두 허용하지 않음
    // 서로 다른 목적과 시점을 갖기에 중복은 아님
    private String name;

    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 생성자를 만들어주지 않아서 500 서버 에러 났었음
    public Schedule(String name, String content) {
        this.name = name;
        this.content = content;
        //this.createdAt = LocalDateTime.now(); //처음에 null 값이 나왔는데 이 코드로 해결
    }

    //service 클래스에서 필요함
    public void update(String content) {
        this.content = content;
    }



}
