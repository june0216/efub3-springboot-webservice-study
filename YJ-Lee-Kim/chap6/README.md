# Chapter 6: AWS 서버 환경을 만들어보자 - AWS EC2

## AWS 회원 가입
https://aws.amazon.com/ko/ 에서 `AWS 계정 생성` 또는 `무료로 시작` 버튼을 클릭하여 회원 가입을 진행한다.

<img width="461" alt="스크린샷 2023-05-08 143058" src="https://user-images.githubusercontent.com/121334671/237008945-6bffb471-e955-4532-95be-47d47891e99b.png">
이때, 무료 기본 플랜을 선택한다.

## EC2 인스턴스 생성하기
EC2는 AWS에서 제공하는 성능, 용량 등을 유동적으로 사용할 수 있는 서버이다.
<br>
<br>
리전을 서울로 변경한 후, 검색창에서 `EC2`를 입력하여 해당 서비스를 선택한다.<br>
그리고 `인스턴스 시작` 버튼을 눌러 인스턴스를 생성하는 페이지에 접속한다.

<img width="635" alt="스크린샷 2023-05-08 144645" src="https://user-images.githubusercontent.com/121334671/237009047-7a6ba24c-fa41-4094-a48c-5011dee9882f.png">


아래와 같이 설정한다.

<img width="640" alt="스크린샷 2023-05-08 144750" src="https://user-images.githubusercontent.com/121334671/237009077-b1874184-7cf6-4a08-93db-38abd97c03a0.png">
<img width="640" alt="스크린샷 2023-05-08 145213" src="https://user-images.githubusercontent.com/121334671/237009297-92afe736-0421-42e8-ab3a-582e2cdcd21d.png">

<img width="640" alt="스크린샷 2023-05-08 145121" src="https://user-images.githubusercontent.com/121334671/237009096-d5a7d45c-c6ce-454c-8111-77a723a6a7bf.png">
<img width="640" alt="스크린샷 2023-05-08 145307" src="https://user-images.githubusercontent.com/121334671/237009335-9268be32-d3ad-495c-992a-0876b1ed4a46.png">
<img width="640" alt="스크린샷 2023-05-08 145315" src="https://user-images.githubusercontent.com/121334671/237009377-96f93fc6-f903-41bf-af93-c489663597fe.png">
<img width="640" alt="스크린샷 2023-05-08 145338" src="https://user-images.githubusercontent.com/121334671/237009416-f55c3beb-9ca6-4684-b4e7-1be2e63ba886.png">
<img width="640" alt="스크린샷 2023-05-08 145346" src="https://user-images.githubusercontent.com/121334671/237009435-0cab6a07-b7c3-4009-a7ff-35dc50c5ccdb.png">
<img width="640" alt="스크린샷 2023-05-08 145353" src="https://user-images.githubusercontent.com/121334671/237009452-21b511f6-9cf3-4cfc-a008-c6b073f0af6a.png">

`인스턴스 시작` 버튼을 눌러 생성 작업을 완료한다.

<br>

## 탄력적 IP 주소 생성 및 연결
AWS의 고정 IP를 탄력적 IP라고 한다.
<br>
왼쪽 카테고리에서 `탄력적 IP`를 눌러 선택하고 `탄력적 IP 주소 할당` 버튼을 클릭한다.

<img width="640" alt="스크린샷 2023-05-08 145508" src="https://user-images.githubusercontent.com/121334671/237009566-4587de51-a3d0-4a83-b137-b2f174c37477.png">

`할당` 버튼을 눌러 생성을 완료한다.

<img width="640" alt="스크린샷 2023-05-08 145532" src="https://user-images.githubusercontent.com/121334671/237009598-70921dbc-ee3e-45cd-bcaf-93ac727560b3.png">


탄력적 IP 주소를 방금 생성한 인스턴스와 연결한다.

<img width="639" alt="스크린샷 2023-05-08 145608" src="https://user-images.githubusercontent.com/121334671/237009632-2fa7aaf1-0a1a-4484-b127-96c0ac2fabb4.png">

<br>

## EC2 서버에 접속하기
### puttygen 
`Conversations`>`import key`를 누른 후 EC2 인스턴스 생성 시 다운받은 pem 파일을 선택한다.
그리고 `Save private key`를 눌러 ppk 파일을 생성한다.

### putty
- Host Name: `ec2-user@탄력적IP주소` 입력
- Port: 22 입력
- Connection type: SSH 선택

왼쪽 사이드바에 있는 `Connection`>`SSH`>`Auth` 이동<br>
`browse` 버튼을 누른 후 방금 생성한 ppk 파일을 불러온다.
<br>
<br>
왼쪽 사이드바에 있는 `Session` 탭으로 이동하여 `Saved Sessions`에서 빈칸에 세션 이름을 입력하고 `Save` 버튼을 눌러 현재 설정을 저장한다.
해당 세션을 더블클릭하면 접속할 수 있다.


## 서버 설정
### java 11 설치
`sudo yum install -y java-1.8.0-amazon-corretto`
<br>
설치 완료 후 `java -version`으로 정상적으로 설치되었는지 확인한다.

### 타임존 변경
```
sudo rm /etc/localtime
sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
```
입력 후 `date` 명령어로 타임존이 변경되었는지 확인한다.



