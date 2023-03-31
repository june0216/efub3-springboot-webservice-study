# Chapter.3

### 💡JPA

---

- `JPA` : 자바 표준 ORM 기술.
  
  - 객체 지향 프로그래밍 언어와 관계형 데이터베이스 사이에서 패러다임 일치를 시켜줌.
  - `ORM` : Object Relational Mapping
- JPA 등장 배경(필요성)
  
  - 객체를 관계형 데이터베이스에서 관리하는 것은 매우 중요함.
    
    - 관계형 데이터 베이스의 예 : Oracle, MySQL …
  - 현업 프로젝트 대부분이 애플리케이션 코드보다 SQL 많음.
    
    - 이유 : 관계형 데이터베이스는 SQL 만 인식 가능한데, 이를 위해 각 테이블마다 CRUD table을 생성해야 하기 때문.
      
    - 발생하는 문제
      
      1. 반복해서 SQL 생성.
        
      2. 패러다임 불일지 문제
        
        - 관계형 데이터베이스로 객체지향을 표현하기 쉽지 않다.
          
        - 때문에 웹 에플리케이션 개발이 데이터 베이스 모델링에만 집중하게 됨.
          
          → 해결 : `JPA` 등장!
          
          개발자는 객체지향적으로 프로그래밍을 하고 SQL에 종속적인 개발을 하지 않아도 됨.
          

### Spring Data JPA

---

- `Spring Data JPA` : JPA 구현체를 추상화하여 더 쉽게 사용할 수 있다.
  
  - `JPA` ← `Hibernate` ← `Spring Data JPA`
    
  - JPA는 인터페이스로서, 원래 구현체를 필요로 하는데 Spring에서는 그렇지 않다.
    
  - 특징
    
    1. 구현체 교체의 용이성
      
      - 내부에서 구현체 매핑을 지원하기 때문에 다른 구현체로 교체가 가능.
    2. 저장소 교체의 용이성
      
      관계형 데이터베이스 외의 다른 저장소로 쉽게 교체 가능.
      
      Spring Data의 하위 프로젝트끼리의 기본적인 CRUD 인터페이스 동일.
      
      ex) Spring Data MongoDB로 의존성 변경
      

### 프로젝트에 Spring Data JPA 적용하기

---

- domain 패키지 : 도메인을 담을 패키지.
- `Entity class`
  - Setter 메소드를 만들지 않음.
  - 기본 Repository를 필요로 함.
    - 인터페이스로 생성.
    - `JpaRepository<Entity 클래스, PK 타입>` 상속 → 기본적인 CRUD 메소드 자동 생성.
    - domain 패키지에서 함께 관리 추천.
  - Request/Response 클래스로 사용하면 안된다.
    - 데이터 베이스와 맞닿아 있는 핵심 클래스. 이 클래스 기준으로 테이블 생성, 스키마 변경.

### 등록/수정/조회 API 만들기

---

- API를 만들기 위해 필요한 3개의 클래스
  
  1. Request 데이터를 받을 Dto
  2. API 요청을 받을 Controller
  3. 트랜잭션,도메인 기능 간의 수정을 보장하는 Service.
    - 비즈니스 로직 처리 X.
- Spring Web 계층
  
  1. Web Layer
    - 뷰 템플릿 영역
    - 외부 요청과 응답에 대한 전반적인 영역
  2. Service Layer
    - Controller과 Dao 중간 영역에서 사용.
  3. Repository Layer
    - DB 등 데이터 저장소에 접근하는 영역
  4. Dtos
    - 계층 간 데이터 교환을 위한 객체의 영역.
  5. Domain Model
    - 비즈니스 처리를 담당하는 Layer.
- JPA 영소
  

### 어려웠던 점

- gradle 버젼을 업그레이드 해야 하는데,,, 그것이 너무 어렵다… 왜 안돼지..ㅠㅠㅠㅠ 일단 더 찾아보기.
  - 이것 때문에 롬북 문제가 해결이 안 되는 것 같다… ㅠㅠㅠㅠ 아님 아예 플젝 하나 새로 파서… 하는 것도 나쁘지 않을 것 같다..
- `import javax.persistence.` 가 안 되어서..
  - File > Settings > Build > Build Tools > Maven > Runner > Delegate IDE build/run actions to Maven 로 해결..
- actual and formal argument lists differ in length 에러..
  - [https://smoh.tistory.com/458](https://smoh.tistory.com/458) 참고해서 해결 ^*^ 행복하다
- 지금 [PostApiController.java](http://PostApiController.java) 에서 에러가 나는데… 아직 해결을 못 했다… ㅠㅠㅠㅠ
