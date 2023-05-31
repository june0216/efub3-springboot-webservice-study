## EC2에 프로젝트 clone 받기
1. EC2에 깃 설치
```
sudo yum install git
```
2. 설치 상태 확인
```
git --version
```
3. git clone으로 프로젝트를 저장할 디렉토리 생성
```
mkdir ~/app && mkdir ~/app/step1
```
4. 생성한 디렉토리로 이동
```
cd ~/app/step1
```
5. 내 깃허브 웹페이지에서 https 주소 복사 후 복사한 주소를 통해 git clone 진행
```
git clone 복사한 주소
```
6. 파일들이 잘 복사 되었는지 확인
```
cd 프로젝트 명
```
7. 코드들이 잘 수행되는지 테스트
```
./gradlew test
```

--------------
## 배포 스크립트 만들기
배포할때마다 하나하나 명령어를 실행하는 것은 불편하니 쉘 스크립트를 작성할 것이다

1. ~/app/step1/에 deploy.sh 파일 생성
```
vim ~/app/step1/deploy.sh
```
2. 아래 코드 추가
```
#!/bin/bash
REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=freelec-springboot2-webservice
cd $REPOSITORY/$PROJECT NAME/
echo "> Git Pull"
git pull
echo "> 프로젝트 Build 시작"
./gradlew build
echo "> step1 디렉토리로 이동"
cd $REPOSITORY
echo "> Build 파일 복사"
cp SREPOSITORY/SPROJECT_NAME/build/libs/*.jar SREPOSITORY/
echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=${pgrep -f ${PROJECT _NAME}.*.jar)
echo "현재 구동 중인 애플리케이션 pid: SCURRENT_PID"
if [ -z "SCURRENT PID" ]; then
echo "> 현재 구동 중인 애플리케이신이 없으므로 종료하지 않습니다."
else
echo "> kill -15 SCURRENT_ PID"
kill -15 SCURRENT_PID
sleep 5
fi
echo "> 새 얘플리케이션 배포"
JAR_NAME=$(Is -tr $REPOSITORY/ | grep jar | tail -n 1)
echo "> JAR Name: $JAR _NAME"
nohup java -jar REPOSITORY/$JAR_NAME 2>&1 &
```
3. 생성한 스크립트에 실행 권한 추가
```
chmod +x ./deploy.sh
```
4. 스크립트 실행
```
./deploy.sh
```
5. nohup.out 파일 열어 로그 확인
```
vim nohup.out
```
→ 로그를 확인해보면 애플리케이션 실행에 실패함을 알 수 있다.

→ ClientRegistrationRepository를 생성하려면 client와 clientSecret이 필수이지만 이 파일은 gitignore로 git 제외 대상이기 때문

→ 서버에서 직접 이 설정들을 가지고 있도록 해야함

---------------------------
## 외부 Security 파일 등록하기
1. app 디렉토리에 properties파일 생성
```
vim /home/ec2-user/app/application-oauth.properties
```
2. 로컬에 있는 application-oauth.properties 파일 내용 복붙

3. 생성한 application-oauth.properties을 쓰도록 desploy.sh 파일 수정
```
...
nohup java -jar \
-Dspring.config.location=classpath:/application.
properties,/home/ec2-user/app/application-oauth.properties \
$REPOSITORY/$JAR_NAME 2>&1 &
```
4. 다시 deploy.sh 실행

