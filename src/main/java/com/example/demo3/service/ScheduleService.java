package com.example.demo3.service;


import com.example.demo3.dto.ScheduleResponse;
import com.example.demo3.entity.Schedule;
import com.example.demo3.repository.ScheduleRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
@Transactional(readOnly = true)
//해당 메서드나 클래스 내부에서 실행되는 데이터베이스 작업은 읽기만 수행한다는 것을 명시함
//쓰기 작업(insert, update, delete)은 하지 않는다는 것을 힌트로 DB 및 JPA에게 제공한다.
// 클래스에 붙이게 되면 쓰기 작업이 있는 메서드는 메서드에서 @Transactional로 다시 명시해줘야 합니다.
//더 이상 더티 체킹(dirty checking) 이나
//쓰기 지연(write-behind) 같은 작업을 하지 않아도 되므로
//내부적으로 성능을 최적화할 수 있음.
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    //생성
    @Transactional
    public ScheduleResponse save(String name ,@NotEmpty String content) {
        // 파라미터(매겨변수)에 유효성 겁사를 하는게 의미가 있나?
        //@NotEmpty는 DTO 클래스 필드에 붙여야 Spring이 유효성 검사(Validation)를 수행합니다.
        //여기처럼 단순 파라미터에 붙이면, 아무 검증도 하지 않습니다.
        Schedule schedule = new Schedule(name,content);
        // 새로운 스케쥴 객체 만들고
        schedule = scheduleRepository.save(schedule);
        // 새로운 객체를 JPA 메서드 save 통해서 저장하고
        return new ScheduleResponse(schedule.getId(),
                schedule.getName(),
                schedule.getContent(),
                schedule.getCreatedAt()
                );
        // Response 형태로 반환함
        // 저장된 엔티티 정보를 기반으로 ScheduleResponse 생성 후 반환
    }


    //id로 조회
    public ScheduleResponse findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return new ScheduleResponse(schedule.getId(),
                schedule.getName(),
                schedule.getContent(),
                schedule.getCreatedAt()
                );
    }

    //전체 조회
    public List<ScheduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();

        for (Schedule schedule : schedules) {
            scheduleResponses.add(new ScheduleResponse(schedule.getId(),
                    schedule.getName(),
                    schedule.getContent(),
                    schedule.getCreatedAt()));
        }
        return scheduleResponses;
    }

    //수정
    @Transactional
    public ScheduleResponse update(Long id ,String content) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다."));
        schedule.update(content);

        return new ScheduleResponse(schedule.getId(),
                schedule.getName(),
                schedule.getContent(),
                schedule.getCreatedAt());
    }

    //삭제
    @Transactional
    public void deleteById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다."));
        //찾을 수 없으면 런타임에러 발생시킴
        scheduleRepository.delete(schedule);
    }

}
