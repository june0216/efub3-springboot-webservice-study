# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스 책 완독 스터디 #1
의지에 가득 차 시작했지만 처음부터 난관이었다.. 책이 2019년도에 나온 책이라 현재 버전과 맞지 않는 부분이 많았기 때문이다.
  <br/><br/>
1. 인텔리제이 2022버전에서 gradle 프로젝트 생성하기 
+ 책에서는 2019버전 인텔리제이를 사용한다. 책을 따라 해보니 현재 내가 사용하고 있는 2022버전과 UI가 많이 달라 gradle 프로젝트 생성이 어려웠다. 
+ 결국 인텔리제이 다운그레이드를 함으로써 해결..!
<br/><br/>
2. 그레이들 프로젝트를 스프링 부트 프로젝트로 변경하기  
+ gradle 기본 코드
```
plugins {
    id 'java'
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
}

test {
    useJUnitPlatform()
}
```
+ plugins
  + 스프링 부트 그레이들, 의존성 관리 플러그인 추가
```
plugins {
    id 'org.springframework.boot' version '2.4.1' // 스프링 부트 그레이들 플러그인
    id 'io.spring.dependency-management' version '1.1.0'// 의존성 관리 플러그인
    id 'java'
}
```
+ repositories: 의존성(라이브러리)들을 어떤 원격 저장소에서 받을지 결정함
  + mavenCentral 추가
  + 책에는 mavenCentral의 단점들을 개선한 jcenter를 추천하지만 현재 지원 중단 되었다..
```
repositories {
    mavenCentral()
}
```
+ dependencies: 프로젝트 개발에 필요한 의존성들(라이브러리) 선언
```
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.9.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.9.2'
    implementation('org.springframework.boot:spring-boot-starter-web')
    testImplementation('org.springframework.boot:spring-boot-starter-test')
}
```
<br/>

3. 깃과 깃허브 사용하기
+ 생략(생성 레포 링크로 대처)
  https://github.com/hannah0226/SpringBootStudy