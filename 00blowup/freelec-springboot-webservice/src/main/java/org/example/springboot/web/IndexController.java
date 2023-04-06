package org.example.springboot.web;

import org.example.springboot.domain.posts.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final PostsService postsService;

    // 책에 있는 대로 위 한 줄만 적었더니 오류가 발생하여 아래의 constructor를 추가하였음
    public IndexController(PostsService postsService) {
        this.postsService = postsService;
    }


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
