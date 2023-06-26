package org.example.springboot.web;

import lombok.RequiredArgsConstructor;
import org.example.springboot.connfig.auth.LoginUser;
import org.example.springboot.connfig.auth.dto.SessionUser;
import org.example.springboot.service.posts.PostsService;
import org.example.springboot.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){ //서버 템플릿 엔진에서 사용할 수 있는 객체를 저장하는 객체
        model.addAttribute("posts", postsService.findAllDesc());
        //postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달함

        //SessionUser user = (SessionUser) httpSession.getAttribute("user");
        //CustomOauth2UserService 에서 로그인 성공 시 세션에 SessionUser 저장
        //index 메소드 외에 다른 컨트롤러와 메소드에서 세션 값이 필요하면 그 때마다 직접 세션에서 값을 가져와야함
        //같은 코드가 계속해서 반복되는 것은 불필요 => 어노테이션을 활용하며 메소드 인자로 세션값을 바로 받을 수 있도록 변경
        
        if(user!=null)
            model.addAttribute("userName2", user.getName());
        return "index"; //호출할 파일 경로 : src/main/resource/templates/index.mustache
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; //호출할 파일 경로 : src/main/resource/templates/posts-save.mustache
    }

    @GetMapping("posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }
}
