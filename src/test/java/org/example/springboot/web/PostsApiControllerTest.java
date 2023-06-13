package org.example.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springboot.domain.posts.Posts;
import org.example.springboot.domain.posts.PostsRepository;
import org.example.springboot.web.dto.PostsSaveRequestDto;
import org.example.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//JAP 기능 작동 X -> MockMVC 사용 X
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 랜덤 포트 실행 -> 실제 내장 톰캣 사용 -> MockMVC X
public class PostsApiControllerTest {

    @LocalServerPort // 사용된 포트 번호
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    /*
    Rest : HTTP URL 을 통해 자원을 명시하고 HTTP Method 를 통해 해당 자원에 대한 CRUD Operation 을 적용하는 것
    Rest Api : Rest의 원리를 따르는 Api
    RestTemplate : 간편하게 Rest Api를 호출할 수 있는 클래스
    TestRestTemplate : 더 간단하게 통합테스트를 할 수 있는 대체안
     */

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    @WithMockUser(roles = "USER")
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") //인증된 모의(가짜) 사용자를 만듦. roles 에 권한 추가 가능
    // ROLE_USER 권한을 가진 사용자가 API 를 요청하는 것과 동일한 효과
    public void Posts_등록된다() throws Exception{
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //사용자의 HTTPRequest에 대한 응답 데이터 포함(HttpStatus, HttpHeaders, HttpBody)
        //HttpEntity<T> 를 상속받음                                          해당 url 로 보낼 객체
        /*
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
                                                           //POST 요청을 보내고 객체로 ResponseEntity 를 받음
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        */

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    @WithMockUser(roles = "USER")
    public void Psots_수정된다() throws Exception{
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId; //@PathVariable 필드

        //사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스(HttpHeader, HttpBody)
        //TestRestTemplate 의 exchange 메소드를 사용하려면 (해당 url로 보낼 객체를 담은)HttpEntity 객체를 넘겨야 함.
        /*
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
                                                           //어떤 HTTP Method 사용 가능   해당 url로 보낼 객체
                                                           //여기서는 Put 요청을 보내려고 exchange 메소드를 사용함
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        */

        //본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON 으로 변환
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
































