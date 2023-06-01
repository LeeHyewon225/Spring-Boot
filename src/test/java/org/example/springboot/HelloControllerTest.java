package org.example.springboot;

import org.example.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
@WebMvcTest(controllers =  HelloController.class)
public class HelloControllerTest {

    @Autowired //스프링이 관리하는 Bean을 주입받음. 기본생성자 호출 시 의존성 주입
    private MockMvc mvc; //컨트롤러 테스트 시 사용. 스프링 MVC 테스트. 실제 서블릿 컨테이너의 구동 없이 테스트용 MVC 환경에서 테스트

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        //MockMvc를 통해 /hello 주소로 HTTP GET 요청
        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
    }

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
