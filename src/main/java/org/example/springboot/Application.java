package org.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//HelloController 에서 테스트 오류 발생. EnableJpaAuditing 를 사용하기 위해선 최소 하나의 Entity 츨래스가 필요하지만
//WebMvcTest 이다 보니 당연히 없음 -> @EnableJpaAuditing 와 @SpringBootApplication 를 분리
//@EnableJpaAuditing //JPA Auditing 활성화

//스프링부트, 스프링 Bean 읽기, 생성을 자동 설정 (기본적인 설정)
//어노테이션 위치부터 설정을 읽기 때문에 항상 최상단에 위치
@SpringBootApplication
public class Application {
    public static void main(String[] args){ // 스프링부트는 main 메소드가 선선된 클래스를 기준으로 실행
        SpringApplication.run(Application.class, args); // 내장 WAS 실행
    }
}
