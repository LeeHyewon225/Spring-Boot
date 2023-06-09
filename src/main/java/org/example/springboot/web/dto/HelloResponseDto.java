package org.example.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter //선언된 모든 필드의 get 메소드 생성
@RequiredArgsConstructor //선언된 모든 final 필드가 포합된 생성자. final 없는 필드는 생성자에 포함 X
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
