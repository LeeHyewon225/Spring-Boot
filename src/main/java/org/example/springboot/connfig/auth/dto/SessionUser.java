package org.example.springboot.connfig.auth.dto;

import lombok.Getter;
import org.example.springboot.domain.user.User;

import java.io.Serializable;

//인증된 사용자 정보만 필요
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

// Serialize 인터페이스 구현 시 직렬화 기능
