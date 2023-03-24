## 01장 인텔리제이로 스프링 부트 시작하기 

### 1.1 인텔리제이 소개
#### 인텔리제이의 강점
- 강력한 추천 기능
- 훨씬 더 다양한 리팩토링과 디버깅 기능
- 이클립스의 깃에 비해 훨씬 높은 자유도
- 프로젝트 시작할 때 인덱싱하여 파일을 비롯한 자원들에 대한 빠른 검색 속도

### 1.2 인텔리제이 설치하기
- 젯브레인(jetbrain) 툴박스를 이용하여 설치

### 1.3 인텔리제이 커뮤니티에서 프로젝트 생성하기
- 이클립스와는 달리 Workspace와 같은 개념 X
- 프로젝트와 모듈의 개념만 존재 ➡️ 한 화면에서는 하나의 프로젝트만 열림
- 단축키는 디폴트 버전을 추천
- Create New Project ➡️ Gradle 선택하여 프로젝트 생성 ➡️ GroupId와 ArtifactId 등록

### 1.4 그레이들 프로젝트를 스프링 부트 프로젝트로 변경하기

> 출처: https://github.com/june0216/efub3-springboot-webservice-study/issues/ 

책의 내용 중 build.gradle에서 버전 차이로 인한 오류가 있으므로 다음의 코드를 참고하여 버전을 작성해주세요!
```java
plugins {
id 'org.springframework.boot' version '2.7.8' // 스프링 부트 그레이들 플러그인
id 'io.spring.dependency-management' version '1.1.0'// 의존성 관리 플러그인 버전
id 'java'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

//각종 의존성(라이브러리)들을 어떤 원격 저장소에서 받을지를 정한다.
repositories {
mavenCentral()
//jcenter() 은 현재 지원 중단
}

//프로젝트 개발이 필요한 의존성들을 선언하는 곳이다.
dependencies {
testImplementation 'org.junit.jupiter:junit-jupiter-api:5.9.2'
testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.9.2'
// 웹에 관련된 의존성 관리. json이나 tomcat등이 들어있다.
implementation('org.springframework.boot:spring-boot-starter-web')
// 테스트를 위한 라이브러리가 들어있다.
testImplementation('org.springframework.boot:spring-boot-starter-test')
}

//Junit5를 사용하기 위해서는 필수로 선언
test {
useJUnitPlatform()
}
```

### 1.5 인텔리제이에서 깃과 깃허브 사용하기 
- `[Ctrl+Shift+A]`를 사용하여 Action 검색창에서 share project on github를 검색
- 깃허브 계정 로그인 ➡️ 깃허브 저장소와 동기화

#### .ignore 파일
- 깃에서 특정 파일 및 디렉토리를 관리 대상에서 제외할 때는 .gitignore 파일 사용
  1) 파일 위치 자동완성
  2) 이그노어 처리여부 확인
  3) 다양한 이그노어 파일 지원

- 인텔리제이에서 자동으로 생성되는 파일들을 모두 이그노어 처리(.gradle, .idea)

#### 깃허브로 Push
- `[Ctrl + K]`로 깃 커밋 창을 열기
- 커밋 파일을 선택하고 메세지를 작성
- `[Ctrl + Shift + K]`로 깃허브 Push
