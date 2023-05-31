# Chapter 8: EC2 서버에 프로젝트를 배포해보자

## EC2에 프로젝트 Clone 받기
### git 설치
`sudo yum install git`
- 설치 상태 확인 
`git --version`

### git clone
- 프로젝트를 저장할 디렉토리 생성 `mkdir ~/app && mkdir ~/app/step1`
- 생성된 디렉토리로 이동 `cd ~/app/step1`
- 깃허브 웹페이지에서 https 주소 복사
- git clone 진행 `git clone 복사한주소`
- 파일들이 잘 복사되었는지 확인
```
cd 프로젝트명
ll
```

### 코드들이 잘 수행되는지 테스트로 검증
`./gradlew test`

### 테스트 실패 시
- 코드 수정 후 깃허브에 푸시
- `git pull`

### gradlew 실행 권한이 없다는 메세지가 뜰 때
`chmod +x ./gradlew`


## 배포 스크립트 만들기
### 배포
작성한 코드를 실제 서버에 반영하는 것
- git clone 혹은 git pull을 통해 새 버전의 프로젝트 받음
- gradle이나 maven을 통해 프로젝트 테스트와 빌드
- EC2 서버에서 해당 프로젝트 실행 및 재실행
<br>

### 쉘 스크립트
확장자가 `.sh`이고, 리눅스에서 기본적으로 사용할 수 있는 스크립트 파일의 한 종류
- 배포할 때마다 개발자가 하나하나 명령어를 실행하는 것은 불편하므로 
쉘 스크립트로 작성해 스크립트만 실행하면 앞의 과정이 차례로 진행되게 함

### deploy.sh 파일 생성 
`vim ~/app/step1/deploy.sh`

### 코드 작성
```
#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=freelec-springboot2-webservice

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"

git pull

echo "> 프로젝트 Build 시작"

./gradlew build

echo ">step1 디렉토리로 이동"

cd $REPOSITORY

echo ">Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f $(PROJECT_NAME).*.jar)

echo "현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
```

### 스크립트에 실행 권한 추가
`chmod +x ./deploy.sh`

### 스크립트 실행
`./deploy.sh`

### 로그 확인
`vim nohup.out`

## 스프링 부트 프로젝트로 RDS 접근하기
### RDS 테이블 생성
테스트 코드수행 시 로그로 생성되는 쿼리와 세션 테이블을 복사하여 RDS에 반영한다.
### 프로젝트 설정
- MariaDB 드라이버를 build.gradle에 등록
`compile("org.mariadb.jdbc:mariadb-java-client)`
- 서버에서 구동될 환경 구성
<br>src/main/resources/에 `application-real.properties` 파일을 만들면 profile=real인 환경이 구성된다.
```
spring.profiles.include=oauth,real-db
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.session.store-type=jdbc
```

### 깃허브로 푸시

### EC2 설정
OAuth와 마찬가지로 RDS 접속 정보도 보호해야 하므로 EC2 서버에 직접 설정 파일을 둔다.
### 설정 파일 생성
`vim ~/app/application-real-db.properties`
### 내용 추가
```
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mariadb://rds주소:포트명(기본은3306)/database이름
spring.datasource.username=db계정
spring.datasource.password=db계정 비밀번호
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```
### deploy.sh 수정
```
...
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-real.properties \
    -Dspring.profiles.active=real \
    $REPOSITORY/$JAR_NAME 2>&1 &
```

## EC2에서 소셜 로그인하기
### AWS 보안 그룹 변경
8080 포트가 보안 그룹에 열려있는지 확인하고, 열려있지 않다면 편집 버튼을 눌러 추가한다.

### AWS EC2 도메인으로 접속
인스턴스의 퍼블릭 DNS에 `:8080`을 붙여 브라우저에 입력하면 접속할 수 있다.

### 구글에 EC2 주소 등록
구글 웹 콘솔 > API 및 서비스 > 사용자 인증 정보에 접속하여 퍼블릭 DNS 주소에 `:8080/login/oauth2/code/google`을 추가하여 승인된 리디렉션 URI에 등록한다.
### 네이버에 EC2 주소 등록
네이버 개발자 센터 > 본인 프로젝트로 이동하여 서비스 URL과 Callback URL 2개를 수정한다.