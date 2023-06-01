package org.example.springboot.dto;

import org.example.springboot.web.dto.HelloResponseDto;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat; // assertj 의 assertThat O. Junit 의 기본 assertThat X


//단순히 롬복을 테스트하기 위한 클래스 -> @RunWith X. @WevMvcTest X. MockMvc X
public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트(){
        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // assertj 라는 테스트 검증 라이브러리의 검증 메소드. 검증하고 싶은 대상을 인자로 받음.
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
