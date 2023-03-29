## 02장 스프링 부트에서 테스트 코드를 작성하자

### 2.1 테스트 코드 소개
#### 테스트 코드를 작성해야하는 이유
- 단위 테스트는 개발단계 초기에 문제를 발견하게 도와준다.
- 단위 테스트는 개발자가 나중에 코드를 리팩토링하거나 라이브러리 업그레이드 등에서 기존 기능이 올바르게 작동하는지 확인할 수 있다.
- 단위 테스트는 기능에 대한 불확싱성을 감소시킬 수 있다.
- 단위 테스트는 시스템에 대한 실제 문서를 제공한다. 즉, 단위 테스트 자체가 문서로 사용할 수 있다.

1) 빠른 피드백
2) 자동 검증 가능
3) 개발자가 만든 기능을 안전하게 보호 

### 2.2 Hello Controller 테스트 코드 작성하기
#### 컨트롤러 클래스 HelloControllerTest 작성 및 API 생성

> 출처: https://github.com/june0216/efub3-springboot-webservice-study/issues/5

➕ JUnit4 → JUnit5 변경 사항
- `@RunWith`: 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다. JUnit4에서는 `@RunWith(SpringRunner.class)`를 붙여서, 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자(여기선 SpringRunner라는 스프링 실행자)를 실행시켰어야 했다. JUnit5부터는 Extention으로 테스트를 실행하는 방법을 커스텀할 수 있으며, `@RunWith(SpringRunner.class)`과 유사하게 `@ExtendWith(SpringExtension.class)`로 Extension 구현체를 지정해줄 수 있다.
- RunWith과 다르게 ExtendWith은 메타 애너테이션으로 사용할 수 있으므로, 스프링 부트가 제공하는 모든 테스트용 애너테이션에 `@ExtendWith(SpringExtension.class)`가 이미 매타 애너테이션으로 적용되어 있어서 생략 가능하다. 여기에는 `@SpringBootTest`와 `@WebMvcTest`가 포함된다.
- `@WebMvcTest`는 여러 스프링 애너테이션 중, Web(Spring MVC)에 집중할 수 있는 애너테이션이다.
-`@SpringBootTest`는 프로젝트의 전체 컨텍스트를 로드하여 모든 빈을 다 가져와 주입하기 때문에 속도가 느리다. 그래서 통합 테스트할 때 주로 사용한다.
- `@WebMvcTest`는 필요한 빈만 가져와서 속도가 빠르다. 보통 컨트롤러 하나를 테스트할 때 주로 사용된다. (SpringBootTest와 동시에 쓰면 에러 발생)
- 선언할 경우 `@controller`, `@ControllerAdvice` 등 웹과 관련된 빈만 주입되어 사용할 수 있다.
- 다만, `@service`, `@component`, `@repository` 등은 빈을 정의하지 못해 사용할 수 없다.
- 클래스 및 메서드에 public을 붙이지 않아도 된다.

> 테스트 코드

```java
package com.jinsim.book.springboot.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloController.class)
// Web(Spring MVC)에 집중할 수 있는 애너테이션. 컨트롤러 하나를 테스트할 때 주로 사용됨
// 내부에 @ExtendWith(SpringExtension.class)가 적용되어 있다.
class HelloControllerTest {
// 클래스 및 메서드에 public 생략 가능

@Autowired
// 스프링이 관리하는 빈을 주입 받는다.
private MockMvc mvc;
// 서블릿을 Mocking한 객체로, 웹 API를 테스트할 때 사용한다.
// 이 클래스를 통해 HTTP Get, Post 등에 대한 API 테스트를 할 수 있다.

@Test
// 해당 메서드가 테스트 메서드임을 명시한다.
void hello가_리턴된다() throws Exception {
    String hello = "hello";

    mvc.perform(get("/hello"))
            // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다.
            // 체이닝이 지원되므로 아래로 mvc.perform의 결과에 대한 검증 기능을 이어서 선언할 수 있다.
            .andExpect(status().isOk())
            // HTTP Header의 Status를 검증한다.(200 404 등 상태 검증, 여기선 200인지 검증)
            .andExpect(content().string(hello));
            // 응답 본문의 내용을 검증한다. HelloController에서 "hello"를 반환하는지 검증한다.- 
}
}
```

### 2.3 롬복 소개 및 설치하기
- 롬복은 자바 개발자들의 필수 라이브러리로, 자바 개발할 때 자주 사용하는 코드 Getter, Setter, 기본 생성자, toString 등을 어노테이션으로 자동 생성해줌.
- build.gradle에 `implementation('org.projectlombok:lombok')` 작성하고 라이브러리 내려받기
- Marketplace에서 LomBok Plugin을 설치
- Setting ➡️ Build, Execution, Deployment ➡️ Annotation Processor ➡️ Enable annotation processing

### 2.4 Hello Controller 코드를 롬복으로 전환하기 
- web 패키지에 dto 패키지 추가
- `HelloResponseDto` 코드 작성
  1) @Getter: 선언된 모든 필드의 get 메소드 생성
  2) @RequiredArgsConstructor: 선언된 모든 final 필드가 포함된 생성자 생성
  
- 새로만든 ResponseDto를 사용하는 코드 추가
  1) @RequestParam: 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
  
- 추가된 API를 테스트 하는 코드를 HelloControllerTest에 추가
  1) param: API 테스트할 때 사용될 요청 파라미터 설정, 값은 String만 허용
  2) jsonPath: JSON 응답값을 필드별로 검증할 수 있는 메소드, $를 기준으로 필드명 명시
  
#### Junit과 비교하여 assertj의 장점
- CoreMatchers와 달리 추가적으로 라이브러리 필요 X
- 자동완성이 확실히 진행
