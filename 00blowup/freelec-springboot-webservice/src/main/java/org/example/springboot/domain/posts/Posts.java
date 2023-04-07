package org.example.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springboot.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter // 롬복. getter를 직접 적지 않아도 이 클래스 내의 필드들의 getter를 사용할 수 있음
@NoArgsConstructor  // 롬복. 아무 매개변수도 받지 않는 기본 생성자를 직접 적지 않아도, 있는 것처럼 사용할 수 있음
@Entity // JPA. 이 클래스가 테이블과 링크될 클래스임을 나타냄
public class Posts extends BaseTimeEntity {

    @Id // 이 어노테이션 이하의 필드가 이 테이블의 primary key임을 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 primary key의 생성 규칙을 나타냄. auto_increment
    private Long id;

    // @Column 어노테이션이 없어도, @Entity를 붙인 클래스 안의 필드는 모두 칼럼으로 등록되기는 함
    @Column(length = 500, nullable = false) // 이 어노테이션은 그냥, 해당 칼럼에 대해 특히 지정해야 하는 옵션이 있을 때 사용함
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder    // 해당 클래스의 빌더 패던 클래스를 선언
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
