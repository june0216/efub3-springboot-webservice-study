## 클라우드
### 클라우드 서비스란?
인터넷을 통해 서버, 스토리지, 데이터베이스, 네트워크, 소프트웨어, 모니터링 등의 컴퓨팅 서비스를 제공하는 것.
## 클라우드 종류

1. Infrastructure as a Service (IaaS, 아이아스, 이에스)
    + 기존 물리 장비를 미들웨어와 함께 묶어둔 추상화 서비스
    + 가상머신, 스토리지, 네트워크,운영체제 등의 IT인프라를 대여해 주는 서비스
    + ex) AWS의 EC2, S3 등
2. Platform as a Service(PaaS, 파스)
   + IaaS에서 한번 더 추상화한 서비스
   + 더 많은 기능이 자동화
   + ex) AWS의 Beanstalk, Heroky 등
3. Software as a Service(SaaS, 사스)
   + 소프트웨어 서비스
   + ex) 구글 드라이브, 드랍박스, 와탭 등

-------------
## EC2 인스턴스 생성하기
### EC2란?
AWS에서 제공하는 성능, 용량 등을 유동적으로 사용할 수 있는 서버
### 진행순서
1. 리전을 서울로 변경
2. 검색창에 EC2검색 후 인스턴스 생성
   + 인스턴스란? EC2 서비스에 생성된 가상머신
3. AMI 선택
   + AMI란? EC2 인스턴스를 시작하는 데 필요한 정보를 이미지로 만들어 둔 것
4. 인스턴스 유형 선택 (→ 프리티어로 표기된 t2.micro 선택)
   + t2는 요금타입을, micro는 사양을 뜻함
5. 스토리지 선택
   + 스토리지란? 흔히 하드디스크라고 부르는 서버의 디스크를 뜻함→서버의 용량을 얼마나 정할지 선택하는 단계
6. 보안그룹 추가
   + 보안그룹이란? 방화벽을 뜻함.
   + 유형에서 SSH이면서 포트에서 22인 경우, AWS EC2에 터미널로 접속할 때를 이야기함.
   + pem키 관리와 지정된 IP에서만 ssh접속이 가능하도록 구성하는 것이 안전
7. 고정 IP(EIP) 할당
   + EC2 인스턴스 페이지의 카테고리에서 탄력적 IP를 선택한 후 할당받기
8. 생성한 탄력적 IP와 EC2 주소 연결.
   + 주의! 탄력적 IP를 생성하고 EC2 서버에 연결하지 않으면 비용이 발생함→ 생성한 탄력적 IP는 무조건 EC2에 바로 연결해야하며 만약 더는 사용할 인스턴슥 없을 때도 탄력적 IP를 삭제해야함

----------------------
## EC2서버에 접속하기
### 진행 순서
1. putty와 puttygen 다운받기
2. putty는 pem키로 사용이 안되므로 puttygen을 사용해 pem 키를 ppk파일로 변환해주기
3. putty를 열어 session에서 HostName, Port, Connection type 등록
   + HostName: username@public_Ip (→ ec2-user@탄력적 IP주소)
   + Port: ssh 접속 포트(→22)
   + Connection type: SSH 선택
4. Connection→SSH→Auth에서 Browser 선택
   + puttygen으로 생성한 ppk파일 불러오기
5. Session에서 Saved Sessions에 저장할 이름 등록하고 저장
6. SSH 접속

-------------------
## 마존 리눅스 1 서버 생성 시 꼭 해야 할 설정들
1. Java 설치
```
sudo apt-get update
sudo apt-get install openjdk-11-jdk

java -version
```
2. 타임존 변경: 기본 서버의 시간은 미국 시간대에기에 한국시간으로 바꿔야한다.
```
date
timadatectl list-timezone|grep Seoul
sudo timedatectl set-timezone Asia/Seoul
```
3. 호스트네임 변경: 현재 접속한 서버의 별명을 등록.
```
hostname
sudo vim/etc/hosts

sudo hostnamectl set-hostname{persistent-hostname}
hostname
sudo reboot
```