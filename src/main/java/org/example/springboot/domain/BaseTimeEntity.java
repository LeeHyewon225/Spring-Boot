package org.example.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //JPA Entity 클래스가 이 클래스를 상속받을 경우 이 필드(createdDate, modifiedDate)들을 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 포함
                                               // 해당 데이터를 보고 있다가 생성 또는 수정이 발생하면 자동으로 값을 넣어주는 기능
public class BaseTimeEntity {
    // 모든 Entity 클래스의 상위 클래스가 되어 Entity들의 createdData, modifiedDate를 자동으로 관리

    @CreatedDate  //Entity 가 생성되어 저장될 때 시간이 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 Entity 의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;
}
