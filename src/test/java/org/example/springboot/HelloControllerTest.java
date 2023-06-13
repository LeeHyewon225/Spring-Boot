package org.example.springboot;

import org.example.springboot.connfig.auth.SecurityConfig;
import org.example.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
@SpringBootTest : Application context 전부 로딩
@RunWith(SpringRunner.class) : @Autowired, @MockBean 등만 Application context로 로딩
                               스트링 부트 테스트와 Junit 사이의 연결자
*/
@RunWith(SpringRunner.class)
@WebMvcTest(controllers =  HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        //WebMvcTest 는 SecurityConfig 는 읽지만 이를 생성하기 위한 CustomOAuth2UserService 는 읽지 못하여 테스트 에러
        //스캔 대상에서 SecurityConfig 제거
    })
public class HelloControllerTest {

    @Autowired //스프링이 관리하는 Bean을 주입받음. 기본생성자 호출 시 의존성 주입
    private MockMvc mvc; //컨트롤러 테스트 시 사용. 스프링 MVC 테스트. 실제 서블릿 컨테이너의 구동 없이 테스트용 MVC 환경에서 테스트

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        //MockMvc 를 통해 /hello 주소로 HTTP GET 요청
        //mvc 는 주소를 통해 연결된 후 반환되는 View 를 테스트하기 위해 사용 (!= assertThat)
        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
    }
    /*
    테스트 실행 시 CustomOauth2UserService 를 생성하는데 필요한 소셜 로그인 관련 설정값들이 없기 때문에 에러 발생
    main 과 test 는 본인만의 환경 구성을 가짐
    다만 test 에 application.properties 가 없으면 main 의 설정을 그대로 가져옴(application.properties 파일만 해당)
    application-oauth.properties 는 test 에 파일이 없다고 가져오지 않음
    -> 가짜 설정값을 등록하는 application.properties 생성
    */

    @WithMockUser(roles = "USER")
    @Test
    public void hellodto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name).param("amount", String.valueOf(amount))) // String 값만 허용
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
                // json 응답값을 필드별로 검증. $을 기준으로 필드명 명시
    }
}
