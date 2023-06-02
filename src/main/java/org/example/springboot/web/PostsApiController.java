package org.example.springboot.web;

import lombok.RequiredArgsConstructor;
import org.example.springboot.service.posts.PostsService;
import org.example.springboot.web.dto.PostsSaveRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor //Bean 주입 받기 : @AutoWierd, setter, *생성자*
@RestController //스프링에서 서비스를 지정하는 어노테이션
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
                    //클라이언트가 json 기반의 HTTP Body 로 POST 요청을 할 경우 이를 자바 객체로 변환해서 받음
        return postsService.save(requestDto);
    }
}
