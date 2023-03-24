package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/* @ExtendWith(SpringExtension.class) */ // 여기서 오류가 발생하는데, 주석처리하면 실행됨,, 좀 더 공부해야함
@WebMvcTest
public class HelloControllerTest {
    @Autowired//스프링이 관리하는 Bean을 주입받기
    private MockMvc mvc; // 웹 API를 테스트할 때 사용(HTTP GET, POST 등), 스프링 MVC 테스트의 시작점
    @Test
    public void hello가_리턴된다() throws Exception
    {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name) // API 테스트할 때 사용될 요청 파라미터 설정, 값은 String만 허용, 숫자, 날짜등의 데이터도 문자열로 변경해야함
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // JSON 응답값을 필드별로 검증할 수 있는 메소드, $기준으로 필드명 명시
                .andExpect(jsonPath("$.amount", is(amount))); // 여기선 $.name과 $.amount로 검증
    }
}

