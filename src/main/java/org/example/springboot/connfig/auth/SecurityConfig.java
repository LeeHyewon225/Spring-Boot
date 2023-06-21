package org.example.springboot.connfig.auth;

import lombok.RequiredArgsConstructor;
import org.example.springboot.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
                                   //스프링 시큐리티 사용을 위한 기본적인 시큐리티 설정
    
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http    .csrf().disable()
                .headers().frameOptions().disable()
                //h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                    .authorizeRequests()//URL 별 권한 관리를 설정하는 옵션의 시작점. 선언되어야 antMatchers 옵션 사용 가능
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    //권한 관리 대상을 지정하는 옵션. 지정된 URL 들은 permitAll 옵션을 통해 전체 열람 권한을 줌
                    //.antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    //권한 관리 대상을 지정하는 옵션. 지정된 URL 주소를 가진 API 는 USER 권한을 가진 사람만 가능
                    .anyRequest().authenticated()
                    //설정된 값들 이외 나머지 URL 들은 모두 인증된 사용자 즉, 로그인한 사용자들에게만 허용
                .and()
                    .logout()//로그아웃 기능에 대한 여러 설정의 진입점
                        .logoutSuccessUrl("/")//로그아웃 성공 시 / 주소로 이동
                .and()
                    .oauth2Login()// OAuth 2 로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint()// OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                            .userService(customOAuth2UserService);
                            //소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현제를 등록
                            //리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
    }
}
