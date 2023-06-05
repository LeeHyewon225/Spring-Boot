package org.example.springboot.service.posts;

import lombok.RequiredArgsConstructor;
import org.example.springboot.domain.posts.Posts;
import org.example.springboot.domain.posts.PostsRepository;
import org.example.springboot.web.dto.PostsResponseDto;
import org.example.springboot.web.dto.PostsSaveRequestDto;
import org.example.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor //Bean 주입 받기 : @AutoWierd, setter, *생성자*
@Service //스프링에서 서비스를 지정하는 어노테이션
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
        /*
       직접 DB에 쿼리를 날리는 부분 X
        JPA 의 영속성 컨텍스트(엔티티 영구 저장)
        트랜잭션 안에서 DB의 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태
        이 상태에서 데이터를 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영
        즉 엔티티의 값만 변경하면 Updata 쿼리를 날릴 필요 X -> 더티 체킹
        */
    }

    @Transactional
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }
}
