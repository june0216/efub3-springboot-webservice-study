# 클라우드 컴퓨팅 (Cloud Computing)

인터넷을 통해 가상화된 컴퓨터의 리소스를 제공하는 것

클라우드 컴퓨팅은 인터넷 기반 컴퓨터의 일종으로 정보를 자신의 컴퓨터가 아닌 인터넷에 연결된 다른 컴퓨터로 처리하는 기술이다. 인터넷에 연결된 다른 컴퓨터가 IT 리소스를 제공해주어서 그 컴퓨터로 처리하는 것이다. IT 리소스는 CPU, 메모리, 스토리지, 네트워크 등을 의미한다.

클라우드 컴퓨팅은 리소스가 필요할 때, 리소스를 사용한 만큼의 돈을 지불하고 사용한다.

따라서 사용자의 요구에 따라 탄력적으로 자가 관리되므로 사용자는 리소스에 대한 관리가 필요 없다.

## 클라우드 컴퓨팅 종류<br>
<img src="/rovemin/img/cloud_computing.jpg"/>

IasS (Infrastructure as a Service)
- IT 서비스 구성을 위한 인프라 제공
- 서버, 스토리지 및 네트워킹 하드웨어, 가상화(하이퍼바이저) 계층을 포함하여 애플리케이션과 서비스를 제공하는 데 필요한 모든 물리적 및 가상 핵심 인프라를 제공하고 호스팅
- ex) AWS, Microsoft Azure, Google Cloud

PasS (Platform as a Service)
- 애플리케이션 및 서비스를 구축할 수 있는 플랫폼 제공
- IasS처럼 물리적 및 가상 핵심 인프라를 제공하고 호스팅하며 미들웨어, DBMS, 개발 툴, 비즈니스 인텔리전스 및 분석 툴 등도 제공
- ex) AWS Elastic Beanstalk, Window Azure, Google App Engine

SasS (Software as a Service)
- 클라이언트 조직의 최종 사용자에게 제공되는 완전한 소프트웨어 솔루션
- 가장 포괄적인 형식의 클라우드 컴퓨팅 서비스로, 모든 애플리케이션은 제공업체가 관리하고 웹 브라우저를 통해 제공
- ex) Google Workspace, Dropbox

---

# AWS (Amazon Web Services)

AWS는 전 세계적으로 분포한 데이터 센터에서 200개가 넘는 완벽한 기능을 서비스를 제공하여 세계적으로 가장 포괄적이며 널리 채택되고 있는 클라우드이다.

핵심 서비스 유형으로는 Compute, Storage, Database, Networking & Content Delivery가 있다.

## AWS 과금 요소

AWS에는 다양 과금 요소가 존재하므로 주의해서 사용해야 한다.

대표적인 과금 요소
- Compute: 독립 가상 서버 제공하는 경우(EC2)가 대표적
- Storage: 각 스토리지 서비스의 사용량에 따라 과금
- Data Transfer: 트래픽을 구별하고, 서비스/구간을 분류하여 전송량에 비례하여 과금

⭐GitHub에 AWS Accesskey를 commit 하지 않도록 주의⭐

GitHub에 모든 코드를 push하면 AWS Accesskey 같은 민감한 정보도 commit되어 민감한 정보가 노출되는 문제가 발생할 수 있으므로 주의해야 한다!!

프리 티어의 경우 EC2에서 인스턴스 1개, RDS에서 인스턴스 1개를 돌린다면 과금이 없다.

그러나 2개 이상의 인스턴스를 돌릴 경우 과금의 위험이 있으므로 주의해야 한다.

AWS는 비용 지불에 대한 연락을 메일로 전달하므로 평소에 자주 확인하는 메일로 계정을 설정하는 것을 권장한다.

##  Amazon EC2 (Amazon Elastic Compute Cloud)
- AWS에서 확장 가능 컴퓨팅 용량을 제공하는 것
- 하드웨어에 선투자할 필요가 없어 더 빠르게 애플리케이션 개발 가능
- 원하는 수의 가상 서버를 구축하고 보안 및 네트워킹을 구성하며 스토리지 관리
- 확장 또는 축소를 통해 요구사항 변경 또는 사용량 스파이크를 처리


1. EC2 인스턴스 생성
   1) Amazon Region을 '아시아 태평양 (서울)'로 설정
   2) 검색창에 EC2를 입력하여 EC2 메뉴로 이동
   3) '인스턴스 시작'을 클릭하여 새 인스턴스 생성
   4) Amazon Machine Image (AMI) 및 인스턴스 유형 선택: Ubuntu
   5) '새 키 페어 생성'을 클릭하여 키 페어 생성
      - 키 페어 이름은 자신이 원하는 이름으로 설정하고 키 페어 유형은 RSA, 프라이빗 키 파일 형식은 .pem으로 설정
      - 키 페어는 다운 받은 후에 다시 다운 받을 수 없기 때문에 안전한 곳에 저장해야 한다!!
   6) 네트워크 설정
      - 보안 그룹 생성을 선택하고 '내 IP에서 SSH 트래픽 허용'을 선택한다
   7) 스토리지 구성
      - 1x 30 GiB gp2 루트 볼륨
   8) 인스턴스 시작


2. 탄력적 IP (Elastic IP) 추가
   1) '탄력적 IP 주소 할당' 클릭
   2) 탄력적 IP 주소 선택
      - 탄력적 IP 주소 화면에서 탄력적 IP 주소 할당 버튼 옆에 있는 '작업' 버튼을 클릭하고 '탄력적 IP 주소 연결' 선택
      - 리소스 유형은 인스턴스를 선택하고, 인스턴스와 프라이빗 IP 주소에 각각 알맞은 값을 입력한 뒤 '연결' 클릭
      - 탄력적 IP는 생성하고 EC2 서버에 연결하지 않으면 비용이 발생하므로 주의해야 한다!!
   3) 인스턴스 정보에서 퍼블릭 IPv4의 주소와 탄력적 IP 주소가 동일한지 확인
      

3. 보안 그룹 설정
   1) 현재 보안 그룹 확인
      - 인스턴스 정보 요약 화면 하단에서 '보안' 클릭
   2) '보안 그룹 생성' 클릭하고 보안 그룹의 이름과 설명 작성
   3) 인바운드 규칙 설정 (*표 참고)
   4) 인스턴스 보안 그룹 변경
      - '작업 → 보안 → 보안 그룹 변경' 클릭
      - 연결된 보안 그룹으로 새로 생성한 보안 그룹 선택하고 저장
   5) 변경된 보안 그룹 확인
   
    [인스턴스 규칙 설정]

    |유형|프로토콜|포트 범위|소스|설명 - 선택 사항|
    |---|---|---|---|---|
    |SSH|TCP|22|내 IP|My Local SSH|
    |HTTP|TCP|80|Anywhere-IPv4|HTTP|
    |HTTPS|TCP|443|Anywhere-IPv4|HTTPS|
    |사용자 지정 TCP|TCP|8080|Anywhere-IPv4|Spring Boot Port|


4. EC2 접속
   1) PuTTY gen 실행
   2) [Conversions] → [import key] → 다운 받은 pem 키 불러오기
   3) Save private key 누르기 (경고 메시지 "예")
   4) ppk 파일 생성 → 생성된 이름과 위치를 등록하라는 창 → 입력
   5) PuTTY Configuration 실행
   6) [Session] → [Host Name (or IP address)] → 탄력적 IP 주소 입력
   7) [Connection] → [SSH] → [Auth] → Browse... 버튼 클릭 → ppk 파일 불러오기
   8) [Session] 탭 이동 → [Saved Sessions] → 현재 설정들을 저장할 이름을 등록하고 [save] → [open] 클릭
   9) login 부분에 ubuntu 입력


5. 설정
- JDK 설치 (Java 11 설치)
```
sudo apt-get update
sudo apt-get install openjdk-11-jdk

java -version
```
- 타임존 변경
```
date
timedatectl list-timezone | grep Seoul
sudo timedatectl set-timezone Asia/Seoul
```
- Hostname 변경
```
hostname
sudo vim /etc/hosts
```
- localhost 옆에 원하는 hostname 작성후 :wq로 vim 빠져나오기
```
sudo hostnamectl set-hostname {persistent-hostname}
hostname
sudo reboot
```

## Amazon RDS (Amazon Relational Database Service)
- 클라우드에서 관계형 데이터베이스를 간편하게 설정, 운영 및 확장
- 하드웨어 프로비저닝, 데이터베이스 설정, 패치 및 백업과 같은 시간 소모적인 관리 작업을 자동화하면서, 비용 효율적이고 크기 조정 가능한 용량을 제공
- 사용자가 애플리케이션에 집중해 애플리케이션에 필요한 빠른 성능, 고가용성, 보안 및 호환성을 제공

1. RDS 인스턴스 생성
   1) 검색창에 RDS를 입력하여 RDS 메뉴로 이동
   2) '데이터베이스 생성'을 클릭하여 RDS 인스턴스 생성
   3) DB 종류 선택
      - 데이터베이스 생성 방식 선택: 표준 생성
      - 엔진 유형: MySQL
   4) DB 설정 입력
      - 템플릿: 프리 티어
      - DB 인스턴스 식별자에 원하는 데이터베이스 이름을 입력하고 마스터 사용자 이름과 마스터 암호 입력
      - 데이터베이스 이름, 마스터 이름, 비밀번호는 실제로 DB에 접근할 때 사용할 정보이므로 신중하게 입력하고 반드시 기억해두어야 한다
   5) 스토리지 설정
      - 스토리지 유형: 범용 SSD(gp2)
      - 할당된 스토리지: 20 GiB
      - 스토리지 자동 조정: '스토리지 자동 조정 활성화'가 기본값으로 체크되어 있는데 체크 해제!! (과금 가능성 있음)
   6) 보안 그룹 설정
      - 퍼블릭 엑세스: 예
      - VPC 보안 그룹(방화벽): 새로 생성 클릭 → 새 VPC 보안 그룹 이름 입력
   7) 추가 구성
      - 백업: '자동 백업을 활성화합니다' 체크 해제
      - 유지 관리: '마이너 버전 자동 업그레이드 사용' 체크 해제


2. 현재 보안 그룹 확인
   1) RDS 인바운드 규칙에 EC2 보안 그룹 ID 입력
      - '인바운드 규칙 편집' 클릭 → 규칙 추가 클릭하여 새 인바운드 규칙 작성 후 저장
      - 새 인바운드 규칙 내용
        - 유형: MYSQL/Aurora
        - 프로토콜: TCP
        - 포트 범위: 3306
        - 소스: 사용자...


3. 파라미터 그룹 설정
   1) '파라미터 그룹 생성'을 클릭하여 파라미터 그룹 생성
      - 그룹 이름(ex. my-rds-parameter-group)과 설명(ex. for test) 작성
   2) Time Zone, Character set, Collation, Max Connection 설정
   3) RDS 파라미터 그룹 변경
      - RDS 인스턴스 이동 → 수정 클릭
      - DB 파라미터 그룹을 새로 생성한 파라미터 그룹으로 변경 (ex. my-rds-parameter-group)
      - DB 인스턴스 수정 사항 확인
      - 수정 예약은 '즉시 적용' 선택하고 'DB 인스턴스 수정' 클릭


4. RDS 접속 테스트
   1) 로컬 PC 접속<br>
   MySQL Workbench에 New Connection 생성
      - Hostname: RDS 엔드포인트
      - Username: RDS 생성 시 입력했던 정보 (마스터 사용자 이름)
      - Password: RDS 생성 시 입력했던 정보 (마스터 암호)
   2) EC2에서 접속
```
# ubuntu에서 MySQL 설치
sudo apt-get update
sudo apt-get install mysql-server

# MySQL 명령어로 접속 시도
mysql -u {마스터 사용자 이름} -p --host {엔드포인트}
```

## EC2 & RDS 삭제하기
1. EC2 중지:인스턴스 중지 
2. EC2 삭제: 인스턴스 종료 
3. EIP(탄력적 IP) 삭제: 작업 → 탄력적 IP 주소 릴리스 
4. 키 페어 삭제: 작업 → 삭제 
5. RDS 삭제: 작업 → 삭제
   - '최종 스냅샷 생성' 선택 해제 후 delete me 입력하여 삭제 (비용 발생 가능성 있으므로 반드시 선택 해제!!)
