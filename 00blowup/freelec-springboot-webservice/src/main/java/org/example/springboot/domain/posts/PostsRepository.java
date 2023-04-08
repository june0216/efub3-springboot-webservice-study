package org.example.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  // 이러한 간단한 쿼리는 직접 작성하지 않고 Jpa로 해결할 수 있으나, 가독성을 위해 직접 작성함
    List<Posts> findAllDesc();
}
