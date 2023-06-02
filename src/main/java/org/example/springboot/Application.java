package org.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//스프링부트, 스프링 Bean 읽기, 생성을 자동 설정 (기본적인 설정)
//어노테이션 위치부터 설정을 읽기 때문에 항상 최상단에 위치
@SpringBootApplication
public class Application {
    public static void main(String[] args){ // 스프링부트는 main 메소드가 선선된 클래스를 기준으로 실행
        SpringApplication.run(Application.class, args); // 내장 WAS 실행
    }
}
