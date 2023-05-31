# Chapter6. AWS 서버 환경을 만들어보자 - AWS EC2

## 클라우드 컴퓨팅 
### 클라우드 컴퓨팅 이전에는?
1) 직접 관리 (90년대 이전): 서버를 직접 회사에서 두고 관리
➡️ 전기 비용, 인터넷 회선 요금, 서버 관리자 인건비가 많이 나옴

2) 서버 호스팅 (90년대 후반): 호스팅 업체의 물리 서버를 단독으로 임대/구매하여 사용
➡️ 초기 구축에 시간과 비용이 많이 소요, 가격 비쌈

3) 클라우드 컴퓨팅 (2010년 전후로 시작 ~): 물리적 서버 1대에 여러 대의 가상 머신을 올려서 사용

- 특정시간에만 트래픽이 몰린다면 유동적으로 사양을 늘릴 수 있는 클라우드가 유리

### 클라우드 컴퓨팅이란?
☑️ 인터넷을 통해 가상화된 컴퓨터의 리소스를 제공하는 것 
- 인터넷 기반 컴퓨터의 일종으로 정보를 자신의 컴퓨터가 아닌 인터넷에 연결된 다른 컴퓨터로 처리하는 기술
- 인터넷에 연결된 다른 컴퓨터가 IT 리소스를 제공해주어서 그 컴퓨터로 처리
- IT 리소스 = CPU, 메모리, 스토리지, 네트워크 등
- 리소스가 필요할 때, 돈을 지불하고 사용
- 탄력적(Elastic)으로 자가 관리(Self-managed) 되므로 리소스에 대한 관리 필요 X


### 클라우드 컴퓨팅의 종류

![](https://velog.velcdn.com/images/chhaewxn/post/24662705-e464-4f12-8b4d-6c19c3c0e1b0/image.png)

🔻 lasS (Infrastructure as a Service)
- IT 서비스 구성을 위한 인프라 제공
- 서버, 스토리지 및 네트워킹 하드웨어, 가상화(하이퍼바이저) 계층을 포함하여 애플리케이션과 서비스를 제공하는데 필요한 모든 물리적 및 가상 핵심 인프라를 제공하고 호스팅
Ex) AWS의 EC2, S3, Microsoft Azure, Google Cloud

🔻 PasS (Platform as a Service)
- 애플리케이션 및 서비스를 구축할 수 있는 플랫폼 제공
- IasS처럼 물리적 및 가상 핵심 인프라를 제공하고 호스팅
+ 미들웨어, DBMS, 개발 툴, 비즈니스 인텔리전스 및 분석 툴 등 제공
Ex) AWS Elastic Beanstalk, Window Azure, Google App Engine

🔻 SasS (Software as a Service)
- 클라이언트 조직의 최종 사용자에게 제공되는 완전한 소프트웨어 솔루션
- 가장 포괄적인 형식의 클라우드 컴퓨팅 서비스로, 모든 애플리케이션은 제공업체가 관리, 웹 브라우저를 통해 제공
Ex) Google Workspace, Dropbox, NetFlix(구독의 방식), I-cloud


## AWS - EC2 & RDS
### AWS(Amazon Web Service)
: 전 세계적으로 분포한 데이터 센터에서 200개가 넘는 완벽한 기능의 서비스를 제공하며 세계적으로 가장 포괄적이며 널리 채택되고 있는 클라우드

### AWS 핵심 서비스 유형
- Compute : EC2 Instance, Container, Serverless, Batch
- Storage : Object Storage, Block Storage, File System Storage, Archive
- Database : Relational DB, NoSQL, In Memory Cache, Data Warehouse
- Networking & Content Delivery : Virtual Private Network, Direct Connect, CDN, DNS, Load Balancing, AutoScaling

### AWS 글로벌 인프라
- 가용영역(AZ): 하나 이상의 데이터 센터, 내결합성을 갖도록 설계
- AZ 리전: 각 리전은 두 개 이상의 AZ로 구성, AWS의 경우 전 세계에 26개의 리전 보유
- 데이터 센터: 일반적으로 수천대의 서버 수용

### AWS 과금 요소(⭐주의)
- Compute : 독립 가상 서버 제공하는 경우(EC2)가 대표적
- Storage : 각 스토리지 서비스에 사용량에 따라 책정
- Data Transfer: 트래칙을 구별하고, 서비스/구간을 분류하여 전송량에 비례해 과금 

특히 Github에 모든 코드를 push하면, AWS Accesskey 같은 민감한 정보 노출 가능
(.gitignore 파일에 주요 파일 넣어놓고 push하기)

## 실습_Amazon EC2 
- AWS에서 확장 가능 컴퓨팅 용량을 제공
- 하드웨어에 선투자할 필요가 없어 더 빠르게 애플리케이션을 개발
- 원하는 수의 가상 서버를 구축하고 보안 및 네트워킹을 구성하며 스토리지를 관리
- 확장 또는 축소를 통해 요구 사항 변경 또는 사용량 스파이크를 처리

#### 1. Amazon Region 설정 
- 사용할 지역으로 선택, 아시아 태평양(서울)

1.2 EC2 메뉴로 이동
1.3 새 인스턴스 생성
1.4 Amazon Machine Image(AMI) 및 인스턴스 유형 선택
- AMI: Ubuntu(조금 더 다루기 쉬움)
🔻 AMI: EC2 인스턴스를 시작하는데 필요한 정보를 이미지로 만들어 둔 것
- 인스턴스 유형: t2.micro 
🔻 AWS에서 무료로 제공하는 프리티어 플랜에서는 EC2 사용에 사양이 t2.micro만 가능하다는 제한이 있음, 다른 사양을 고르면 비용 청구됨

1.5 키페어 생성 
- 프라이빗 키 파일 형식 .pem으로 생성하고 안전한 곳에 저장
- 일종의 마스터키이므로 절대 유출되면 안됨
- .pem키는 이후 EC2 서버로 접속할 때 필수 파일이니 잘 관리할 수 있는 디렉토라에 저장

1.6 네트워크 설정: 보안 그룹 생성
1.7 스토리지 구성
- 스토리지는 하드디스크라고 부르는 서버의 디스크를 이야기하며 서버의 용량을 얼마나 정할지 선택하는 단계
1.8 인스턴스 시작

### 탄력적 IP를 사용하는 이유
- 인스턴스도 결국 하나의 서버이기에 IP가 존재함. 인스턴스를 중지하고 다시 시작할 때도 새 IP가 할당되므로 매번 변경되지 않고 고정 IP를 가지게 해야한다!

#### 2. 탄력적 IP
2.1 탄력적 IP 할당
2.2 탄력적 IP 주소 선택
2.3 인스턴스 선택 및 연결
- 탄력적 IP는 생성하고 EC2 서버에 연결하지 않으면 비용 발생 주의
➡️ 생성한 탄력적 IP는 무조건 EC2에 바로 연결해야함

2.4 인스턴스 정보 확인
- 퍼블릭 IPv4 주소, 탄력적 IP 주소: 동일 확인 

3.1 현재 보안 그룹 확인 
3.2 보안 그룹 생성
- 보안그룹은 방화벽을 이야기함. '서버로 80포트 외에는 허용하지 않는다'는 역할을 하는 방화벽이 AWS에서는 보안그룹으로 사용된다.
- 보안은 높을수록 좋으니, pem 키 관리와 지정된 IP에서만 SSH 접속이 가능하도록 구성하는 것이 안전하다. 그 외의 장소에서 접속할 때는 해당 장소의 IP를 다시 SSH 규칙에 추가하는 것이 안전함
3.3 인바운드 규칙 
- SSH, HTTP, HTTPS, 사용자 지정 TCP

3.4 인스턴스 보안 그룹 변경 
3.5 변경된 보안 그룹 확인

#### 4. EC2 접속
4.1 PuTTy gen 실행 
- putty는 pem 키로 사용이 안되며 pem 키를 ppk 파일로 변환을 해야만 한다 이때 필요한 클라이언트가 puttygen
4.2 Conversions ➡️ import key ➡️ 다운로드 받은 pem 키 불러오기
4.3 Save private key 누르기 
4.4 ppk 파일 생성 ➡️ 생성된 이름과 위치를 등록하라는 창 
4.5 Session ➡️ Host Name(or IP address) ➡️ 탄력적 IP 주소 입력
4.6 Connetion ➡️ SSH ➡️ Auth ➡️ Browse... 버튼 클릭 ➡️ ppk 파일 불러오기
4.7 Session 탭이동 ➡️ Saved Sessions ➡️ 현재 설정들을 저장할 이름을 등록하고 save ➡️ open 클릭
- login 부분에 ubuntu에 입력하면 SSH 접속 성공 

#### 5. 아마존 리눅스 1 서버 생성 시 꼭 해야할 설정들
5.1 JDK 설치 (Java11 설치)
```
sudo apt-get update
sudo apt-get install openjdk-11-jdk

java -version
```
5.2 Hostname 변경
```linux
hostname
sudo vim /etc/hosts

sudo hostnamectl set-hostname {persistent-hostname}
hostname
sudo reboot
```
