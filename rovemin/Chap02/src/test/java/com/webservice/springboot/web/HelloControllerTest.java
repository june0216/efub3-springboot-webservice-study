package com.webservice.springboot.web;

import com.webservice.springboot.web.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = HelloController.class)    // Web(Spring MVC)에 집중할 수 있는 어노테이션
                                                        // 내부에 @ExtendWith(SpringExtension.class) 적용되어 있음
    // 선언 시 @Controller, @ControllerAdvice 등은 사용 가능하지만, @Service, @Component, @Repository 등은 사용 불가
public class HelloControllerTest {

    @Autowired  // 스프링이 관리하는 빈(Bean)을 주입받음
    private MockMvc mvc;    // 웹 API 테스트 시 사용. 스프링 MVC 테스트의 시작점
                            // 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))  // MockMvc 통해 /hello 주소로 HTTP GET 요청
                                              // 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언 가능
                .andExpect(status().isOk())             // mvc.perform의 결과 검증. HTTP Header의 Status 검증
                                                        // 200, 404, 500 등의 상태를 검증하는데 여기선 OK 즉, 200인지 아닌지 검증
                .andExpect(content().string(hello));    // mvc.perform의 결과 검증. 응답 본문의 내용 검증
                                                        // Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform((get("/hello/dto")
                            .param("name", name)
                            .param("amount", String.valueOf(amount))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(name)))
                    .andExpect(jsonPath("$.amount", is(amount)));
    }
}
