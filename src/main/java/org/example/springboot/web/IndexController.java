package org.example.springboot.web;

import lombok.RequiredArgsConstructor;
import org.example.springboot.service.posts.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){ //서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        model.addAttribute("posts", postsService.findAllDesc());
        //postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달함
        return "index"; //호출할 파일 경로 : src/main/resource/templates/index.mustache
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; //호출할 파일 경로 : src/main/resource/templates/posts-save.mustache
    }

}
