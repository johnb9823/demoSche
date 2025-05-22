package com.example.demo3.controller;

import com.example.demo3.dto.ScheduleRequest;
import com.example.demo3.dto.ScheduleResponse;
import com.example.demo3.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules") // 공통 url
@RequiredArgsConstructor // 생성자 자동 생성
public class ScheduleController {

    private final ScheduleService scheduleService;

    //등록
    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@RequestBody @Valid ScheduleRequest scheduleRequest) {
        //@RequestBody -> 클라이언트가 요청한 Json 데이터를 ScheduleRequest 객체로 변환해줌
        //@Valid -> 필수값 유효성 겁사
        // ResponseEntity<ScheduleResponse> 반환타입
        // HTTP 응답을 만들 때 사용. (상태 코드 + 본문 데이터를 포함)
        ScheduleResponse response = scheduleService.save(scheduleRequest.getName(),scheduleRequest.getContent());
        // 클라이언트가 요청한 데이터에서 Content를 꺼내서 scheduleService 클래스의 save 메소드를 이용해서 저장하고
        // 결과로  ScheduleResponse 객체를 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
        //클라이언트에서 HTTP 201 Created 상태코드를 반환하고 본문에는 ScheduleResponse 객체가 들어감.
    }

    // Id 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable Long id) {
        // URL 경로에서 {id}에 해당하는 값을 받아옴
        ScheduleResponse response = scheduleService.findById(id);
        // Id 값을 scheduleService 클래스의 findById 메소드를 통해서 찾아서
        // ScheduleResponse 형태로 반환됨
        return ResponseEntity.ok(response);
        //200 ok 와 response 형태로 클라이언트에게 반환됨
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAll() {
        // 전체조회는 클라이언트로부터 받은 데이터 없음
        // List 형태로 ScheduleResponse 반환함 (json 배열 형태)
        List<ScheduleResponse> responseList = scheduleService.findAll();
        //finaAll 메서드 호출로 모든 일정 테이터를 가져와서
        //ScheduleResponse 객체로 변환한 리스트를 반환함
        return ResponseEntity.ok(responseList);
        //200 ok 와 response 형태로 클라이언트에게 반환됨
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> updateById(
            @PathVariable Long id,
            // URL 경로에서 {id}에 해당하는 값을 받아옴
            @RequestBody @Valid ScheduleRequest scheduleRequest) {
            // 클라이언트 요청을 ScheduleRequest 객체로 변환, 유효성 검사
        ScheduleResponse updatedSchedule = scheduleService.update(id, scheduleRequest.getContent());
        //id에 해당하는 일정을 찾아서 content를 수정한 후 결과를 ScheduleResponse 형식으로 반환
        return ResponseEntity.ok(updatedSchedule);
        // 200 Ok Response 형태로 클라이언트에게 반환됨
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        // 사용자에서 다시 반환해줄 데이터가 필요 없으니 ResponseEntity<String> 스트링형식으로 함
        scheduleService.deleteById(id);
        //Service에서 deleteById 메소드 수행
        return ResponseEntity.ok("Delete Success");
        //200 Ok Delete Success 메시지 반환.
    }
}
