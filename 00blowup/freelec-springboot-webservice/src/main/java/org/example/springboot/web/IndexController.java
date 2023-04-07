package org.example.springboot.web;

import org.example.springboot.config.auth.dto.SessionUser;
import org.example.springboot.domain.posts.PostsService;
import org.example.springboot.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    // 책에 있는 대로 위의 선언문만 적었더니 오류가 발생하여 아래의 constructor를 추가하였음
    public IndexController(PostsService postsService, HttpSession httpSession) {
        this.postsService = postsService;
        this.httpSession = httpSession;
    }


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate (@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
