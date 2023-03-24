# Chapter 1: 인텔리제이로 스프링 부트 시작하기

## gradle 프로젝트를 스프링 부트 프로젝트로 변경하기

---

### 프로젝트의 플러그인 의존성 관리를 위한 설정
``` java
buildscript {
    ext {
        springBootVersion = '2.1.7.RELEASE'
    }
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}
```
ext: `build.gradle`에서 사용하는 전역변수를 설정

---

### 자바와 스프링 부트를 사용하기 위한 필수 플러그인
``` java
plugins {
    id 'org.springframework.boot' version '2.7.8' 
    id 'io.spring.dependency-management' version '1.1.0'
    id 'java'
}
```
io.spring.dependency-management: 스프링 부트의 의존성 관리 플러그인

---

### repositories
각종 의존성(라이브러리)들을 어떤 원격 저장소에서 받을지 정함
``` java
repositories {
    mavenCentral()
}
```
---

### dependencies
프로젝트 개발에 필요한 의존성들을 선언
``` java
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
    // 웹에 관련된 의존성 관리. json이나 tomcat 등이 들어있다.
    implementation('org.springframework.boot:spring-boot-starter-web')
    // 테스트를 위한 라이브러리가 들어있다.
    testImplementation('org.springframework.boot:spring-boot-starter-test')
}
```
