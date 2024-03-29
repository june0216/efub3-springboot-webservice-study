package org.example.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void 메인페이지_로딩() {
        // when
        String body = this.restTemplate.getForObject("/", String.class);
        
        // then
        // 불러온 index.html 내에 "스프링 부트로 시작하는 웹 서비스" 문자열이 포함되어있는지 확인
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }
}
