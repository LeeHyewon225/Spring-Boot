package org.example.springboot.web;

import org.example.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
@Controller
ViewName 반환
데이터 반환 시 @RespenseBody 사용 -> json 형태로 데이터 반환

@RestController(== @Controller + @ResponseBody)
json 형태로 객체 데이터를 반환하는 컨트롤러
*/
@RestController
public class HelloController {

    //@RequestMapping(method = RequestMethod.GET, path = "/hello")와 동일
    //HTTP Method인 Get의 요청을 받고 path값을 해당 url에 매핑
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
                                    //외부에서 API로 넘긴 파라미터를 가져옴
                                    //"name" 이란 이름으로 넘긴 파라미터를 String name에 저장
        return new HelloResponseDto(name, amount);
    }
}
