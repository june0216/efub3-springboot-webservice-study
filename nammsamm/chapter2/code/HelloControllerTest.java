package awsStudy.web;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
@WebMvcTest(controllers = HelloController.class) // @Controller 과 @ControllerApplication 사용 가능

public class HelloControllerTest {
    @Autowired // 스프링이 관리하는 bean을 주입받음.
    private MockMvc mvc; //웹API를 테스트할 때 사용. 스프링 MVC 테스트의 시작점.

    @Test
    public void hello가_리턴된다() throws  Exception{
        String hello="hello";

        mvc.perform(get("/hello"))//MockMvc를 통해 /hello 주소로 HTTP GET 요청.
                .andExpect(status().isOk()) //HTTP Header의 Status를 검증
                .andExpect((content().string(hello))); //mvc.perform 의 결과를 검증.
    }

    @Test
    public void hellodto가_리턴된다() throws Exception{
        String name="hello";
        int amount=1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name",name)//
                        .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name",is(name))) ///
                .andExpect((ResultMatcher) jsonPath("$.amount",is(amount)));
    }
    public void testHello() {
    }
}
