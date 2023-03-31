package com.webservice.springboot.domain.posts;

import com.webservice.springboot.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}
