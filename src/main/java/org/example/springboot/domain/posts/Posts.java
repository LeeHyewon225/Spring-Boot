package org.example.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//주요 어노테이션을 클래스에 가깝게 둠
@Getter //Lombok, 클래스 내 모든 필드의 Getter 메소드 자동 생성
@NoArgsConstructor //Lombok, 기본 생성자 자동 추가
@Entity
//JPA, JPA 를 사용하여 DB 테이블과 매핑할 클래스
//절대 Setter 메소드를 만들지 않음 -> 명확히 그 목적과 의도를 나타낼 수 있는 메소드 추가
public class Posts {

    @Id //PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long id;

    @Column(length = 500, nullable = false) //테이블의 칼럼. 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼. 기본값 외에 추가로 변경할 옵션이 있을 결우 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author; //@Column 을 사용하지 않았지만 자동으로 테이블의 칼럼

    /*
    클래스 또는 생성자 위에 선언. 생성자 위에 선언 시 생성자에 포함된 필드만 빌더에 포함
    정보를 받은 후에 객체 생성 -> 데이터 일관성
    setter 메소드 X -> 객체 불변성 만족
    명시적 선언
    해당 클래스의 빌더 패턴 클래스 생성 -> 빌더를 통해 객체 생성 -> 생성 시 DB에 값을 채워줌
    */
    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
