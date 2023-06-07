package org.example.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/*
JpaRepository. 보통 ibatis 나 MyBatis 등에서 Dao 라고 불리는 DB Layer 접근자
JPA 에선 Repository 라고 부르며 인터페이스로 생성
*** Entity 클래스와 기본 Entity Repository 는 함께 위치해야 함***
*/


public interface PostsRepository extends JpaRepository<Posts, Long> {
                    //단순히 인터페이스 생성 후 JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드 자동 생성

    //SpringDataJpa 애서 제공하지 않는 메소드는 쿼리로 작성해도 됨
    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();
}
