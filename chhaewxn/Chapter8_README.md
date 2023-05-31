> ➕ 스프링 부트외 AWS로 혼자 구현하는 웹서비스 
- Chapter8. EC2 서버에 프로젝트를 배포해보자

### EC2에 프로젝트 Clone 받기

- 깃허브에 코드를 받아올 수 있게 EC2에 git 설치
```shell
sudo yum install git
git --version //버전 확인
```

- git clone으로 프로젝트에 저장할 디렉토리 생성
```shell
mkdir ~/app && mkdir ~/app/step1
cd ~/app/step1 //생성된 디렉토리로 이동 
```

- github에서 https 주소를 복사하고 복사한 주소를 통해 git clone을 진행 
```shell
git clone 복사한 주소
cd 프로젝트명
ll # 파일이 잘 복사되었는지 확인 
./gradlew test # 코드들이 잘 수행되는지 테스트로 검증
```

- 여기서 테스트 실패시 코드 수정 후에 깃허브에 푸시
```shell
git pull
chmod +x ./gradlew # 실행 권한을 추가한 뒤 테스트 실행
```

➕ 참고
- EC2엔 그레이들(Gradle)을 설치하지 않았다.
- 하지만 Gradle Task(ex: test)를 수행할 수 있다.
- 이는 프로젝트 내부에 포함된 gradlew 파일 때문이다.
- Gradle이 설치되지 않은 환경 혹은 버전이 다른 상황에서도 해당 프로젝트에 한해서 그레이들을 쓸 수 있도록 지원하는 Wrapper 파일이다.
- 해당 파일을 직접 이용하기 때문에 별도로 설치할 필요가 없다.

### 배포 스크립트 만들기
🔻배포: 작성한 코드를 실제 서버에 반영하는 것 
- git clone or git pull을 통해 새 버전 프로젝트 받음
- Gradle이나 Maven을 통해 프로젝트 테스트와 빌드
- EC2 서버에서 해당 프로젝트 실행 및 재실행 

#### 빔(vim)
- 리눅스 환경과 같이 GUI가 아닌 환경에서도 사용할 수 있는 편집 도구 
- 빔 코드 추가
```shell

#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
// 프로젝트 디렉토리 주소는 스크립트 내에서 자주 사용하는 값이기 때문에 이를 변수로 저장
// 쉘에서는 타입 없이 선언하여 저장
// 쉘에서는 $변수명으로 변수 사용가능 
PROJECT_NAME=freelec-springboot2-webservice

cd $REPOSITORY/$PROJECT_NAME/
// 제일 처음 git clone 받았던 디렉토리로 이동
// 바로 위의 쉘 변수 설명을 따라 주소로 이동

echo "> GIT PULL"

git pull
// 디렉토리 이동 후, master 브랜치의 최신 내용을 받기
echo "> 프로젝트 Build 시작"

./gradlew build 
// 프로젝트 내부의 gradlew로 build 수행
echo "> step1 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
// build의 결과물인 jar 파일을 복사해 jar파일을 모아둔 위치로 복사
echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=${pgrep -f ${PROJET_NAME}.*.jar)
// 기존에 수행 중이던 스프링부트 애플리케이션 종료
// pgrep은 process id만 추출하는 명령어
// -f 옵션은 프로세스 이름으로 찾기

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
// 현재 구동 중인 프로세스가 있는지 없는지를 판단하여 기능 수행
// process id를 보고 프로세스가 있으면 해당 프로세스 종료
	echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
	echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
// 새로 실행할 jar 파일명 찾기
// 여러 jar 파일이 생기기 때문에 tail -n로 가장 나중의 jar 파일(최신 파일)을 변수에 저장

echo "> JAR Name: $JAR_NAME"

nohup java jar $REPOSITORY/$JAR_NAME 2>&1 &
// 찾은 jar파일명으로 해당 jar 파일을 nohup으로 실행
// 스프링부트의 장점으로 외장 톰캣 설치할 필요가 없음
// 내장 톰캣을 사용해서 jar 파일만 있으면 바로 웹 애플리케이션 서버 실행가능
// 자바를 실행할 때는 java -jar라는 명령어를 사용하지만, 이렇게 하면 사용자가 터미널 접속을 끊을 때 애플리케이션도 같이 종료됨
// 애플리케이션 실행자가 터미널을 종료해도 애플리케이션은 계속 구동될 수 있도록 nohup 명령어를 사용 
```
이렇게 생성하고나서, nohup.out 파일을 열어보면 오류 발생!

### 외부 Security 파일 등록 
ClientRegistrationRepository를 생성하려면 clientId와 clientSecret가 필수이다. 로컬PC에서 실행할 때는, application-oauth.properties 가 있어 문제가 없었지만 .gitignore로 git에서 제외 대상이기에 깃허브에 올라가있지 않음

➡️ 애플리케이션을 실행하기 위해 서버에서 직접 이 설정들을 가지고 있게 하자!

- app 디렉터리에 properties 파일을 생성한다.
- 로컬에 있는 application-oauth.properties 파일 내용을 그대로 붙여넣는다. 그리고 생성한 application-oauth.properties을 쓰도록 deploy.sh 파일을 수정한다.

```shell
nohup java -jar \
     -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties \
        $REPOSITORY/$JAR_NAME 2>&1 &
// 스프링 설정 파일 위치를 지정한다.
// 기본 옵션들을 담고 있는 application.yml과 OAuth 설정들을 담고 있는 application-oauth.yml의 위치를 지정한다.
// classpath가 붙으면 jar 안에 있는 resources 디렉토리를 기준으로 경로가 생성된다.
// application-oauth.yml 은 절대경로를 사용한다. 외부에 파일이 있기 때문이다.  
```
수정 후에 정상 실행

### 스프링 부트 프로젝트로 RDS 접근하기
- 책에서는 MariaDB를 사용하지만, 나는 MySQL에서 프로젝트를 실행하려함.

🔻진행할 작업
- 테이블 생성 : H2에서 자동 생성해주던 테이블들을 MYSQL에선 직접 쿼리를 이용해 생성한다.
- 프로젝트 설정 : 자바 프로젝트가 MYSQL에 접근하려면 데이터베이스 드라이버가 필요하다. MYSQL에서 사용 가능한 드라이버를 프로젝트에 추가한다.
- EC2 (리눅스 서버) 설정 : 데이터베이스의 접속 정보는 중요하게 보호해야 할 정보이다. 공개되면 외부에서 데이터를 모두 가져갈 수 있기 때문이다. 프로젝트 안에 접속 정보를 갖고 있다면 깃허브와 같이 오픈된 공간에선 누구나 해킹할 위험이 있다. EC2 서버 내부에서 접속 정보를 관리하도록 설정한다. 

### RDS 테이블 생성
- 여기서 JPA가 사용될 엔티티 테이블과 스프링 세션이 사용될 테이블 2가지 종류를 생성한다. JPA가 사용할 테이블은 테스트 코드 수행 시 로그로 생성되는 쿼리를 사용하면 된다.

🔻`schema-mysql.sql` 파일
```sql
CREATE TABLE SPRING_SESSION (  
   PRIMARY_ID CHAR(36) NOT NULL,  
   SESSION_ID CHAR(36) NOT NULL,  
   CREATION_TIME BIGINT NOT NULL,  
   LAST_ACCESS_TIME BIGINT NOT NULL,  
   MAX_INACTIVE_INTERVAL INT NOT NULL,  
   EXPIRY_TIME BIGINT NOT NULL,  
   PRINCIPAL_NAME VARCHAR(100),  
   CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)  
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;  
  
CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);  
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);  
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);  
  
CREATE TABLE SPRING_SESSION_ATTRIBUTES (  
   SESSION_PRIMARY_ID CHAR(36) NOT NULL,  
   ATTRIBUTE_NAME VARCHAR(200) NOT NULL,  
   ATTRIBUTE_BYTES BLOB NOT NULL,  
   CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),  
   CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE  
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;
```

### 프로젝트 생성
- MYSQL 드라이버를 build.gradle에 등록한다

```java
implementation 'mysql:mysql-connector-java' // mysql
```
-그리고 서버에서 구동될 환경을 하나 구성한다.
src/main/resource에 application-real.properties 파일을 추가
```java
spring:  
  profiles:  
    include: oauth, real-db  
  jpa:  
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect  
  session:  
    store-type: jdbc
```
- application-real.properties로 파일을 만들면 profile=real인 환경이 구성된다고 보면 된다.

- 실제 운영될 환경이기 때문에 보안/로그상 이슈가 될 만한 설정들을 모두 제거하며 RDS 환경 profile 설정이 추가된다.

### EC2 설정
OAuth와 마찬가지로 RDS 접속 정보도 보호해야 할 정보이니 EC2 서버에 직접 설정 파일을 둔다. app디렉토리에 application-real-db.properties 파일을 생성한다.

```shell
vim ~/app/application-real-db.yml
```

그리고 다음 내용을 추가
```java
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://rds주소: 기본은 3306/db이름
    username: db계정명
    password: db계정 비밀번호
  jpa:
    hibernate:
      ddl-auto: none
//JPA로 테이블이 자동 생성되는 옵션을 None(생성하지 않음)으로 지정한다.
// RDS에는 실제 운영으로 사용될 테이블이니 절대 스프링 부트에서 새로 만들지 않도록 해야 한다.
// 이 옵션을 하지 않으면 자칫 테이블이 모두 새로 생성될 수 있다.
// 주의해야 하는 옵션
```

- deploy.sh에 real profile을 쓸 수 있도록 다음과 같이 개선
```shell
nohup java -jar \
        -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/application-oauth.yml,/home/ec2-user/app/application-real-db.yml,classpath:/application-real.yml \
        -Dspring.profiles.active=real \
        $REPOSITORY/$JAR_NAME 2>&1 &
```

### EC2에서 소셜 로그인하기
#### AWS 보안 그룹 변경
- EC2에 스프링 부트 프로젝트가 8080 포트로 배포되었으니, 8080 포트가 보안 그룹에 열려 있는지 확인한다. 

#### AWS EC2 도메인으로 접속 
- 인스턴스 메뉴에서 생성한 EC2 인스턴스를 선택하면 퍼블릭 DNS를 확인할 수 있다.
- 퍼블릭 DNS가 EC2에 자동으로 할당된 도메인이다. 인터넷이 되는 장소 어디나 이 주소를 입력하면 우리의 EC2 서버에 접근할 수 있다.
- 도메인 주소에 8080 포트를 붙여 브라우저에 입력하면 프로젝트가 도메인을 가지게 된다!

#### 구글에 EC2 주소 등록
1) 구글 웹 콘솔(https://console.cloud.google.com/home/dashboard)로 접속하여 프로젝트로 이동한 다음 API 및 서비스 → 사용자 인증 정보로 이동한다.

2) `OAuth 동의 화면` 탭을 선택하고 아래에서 승인된 도메인에 http:// 없이 EC2의 퍼블릭 DNS를 등록한다.

3) `사용자 인증 정보` 탭을 클릭해서 본인이 등록한 서비스의 이름을 클릭한다.

4) 퍼블릭 DNS 주소에 `:8080/login/oauth2/code/google` 주소를 추가하여 승인된 리디렉션 URI에 등록한다.

#### 네이버에 EC2 주소 등록
1) 네이버 개발자 센터(https://developers.naver.com/apps/#/myapps)로 접속해서 본인의 프로젝트로 이동한다.

2) 아래로 내려가 보면 PC 웹 항목이 있는데 여기서 서비스 URL와 Callback URL 2개를 수정한다.

☑️ **서비스 URL**
- 로그인을 시도하는 서비스가 네이버에 등록된 서비스인지 판단하기 위한 항목이다.
- 8080 포트는 제외하고 실제 도메인 주소만 입력한다.
- 네이버에서 아직 지원되지 않아 하나만 등록 가능하다.
- 즉, EC2의 주소를 등록하면 localhost가 안된다.
- 개발 단계에서는 등록하지 않는 것을 추천한다.
- localhost도 테스트하고 싶으면 네이버 서비스를 하나 더 생성해서 키를 발급받으면 된다.

☑️ **Callback URL**

- 전체 주소를 등록한다. (EC2 퍼블릭 : DNS:8080/login/oauth2/code/naver)

🔻**문제점**

- 수동 실행되는 Test
: 본인이 짠 코드가 다른 개발자의 코드에 영향을 끼치지 않는지 확인하기 위해 전체 테스트를 수행해야만 한다.
: 현재 상태에선 항상 개발자가 작업을 진행할 때마다 수동으로 전체 테스트를 수행해야만 한다.

- 수동 Build
: 다른 사람이 작성한 브랜치와 본인이 작성한 브랜치가 합쳐졌을 때(Merge) 이상이 없는지는 Build를 수행해야만 알 수 있다.
: 이를 매번 개발자가 직접 실행해봐야만 한다.
