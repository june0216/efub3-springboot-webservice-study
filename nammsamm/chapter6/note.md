# 클라우드 컴퓨팅

**인터넷을 통해 가상화된 컴퓨터의 리소스를 제공하는 것.**

- 인터넷 기반 컴퓨터의 일종.
  - 정보를 인터넷에 연결된 다른 컴퓨터로 처리.
  - 인터넷에 연결된 다른 컴퓨터가 `IT 리소스`를 제공해주어서 그 컴퓨터로 처리.
    - `IT 리소스` : CPU, 메모리, 스토리지, 네트워크 등
- On-demand, Usage-meterd
  - 리소스가 필요할 때, 리소스를 사용한 만큼의 돈을 지불하고 사용.
  - 사용자의 요구에 따라 탄력적으로 자가 관리됨. (Elastic self-managed)
  - 사용자는 리소스에 대한 관리 필요 없음.

### 클라우드 컴퓨팅 종류

1. `**IaaS**`
  
  **Infrastructure as a service**
  
  - IT서비스 구성을 위한 인프라 제공.
    
  - 애플리케이션과 서비스를 제공하는 데 필요한 모든 물리적 및 가상 핵심 인프라를 제공, 호스팅
    
    - 서버, 스토리지, 네트워킹 하드웨어, 가상화(하이퍼바이저) 계층 포함.
    
    ex) `AWS` , microsoft azure, google cloud
    
2. `**PasS**`
  
  **Platform as a service**
  
  - 애플리케이션 및 서비스를 구축할 수 있는 플랫폼 제공.
    
  - 물리적 및 가상 핵심 인프라를 제공하고 호스팅.
    
    ex) AWS Elastic Beantalk , Window Azure, Google App Engine
    
3. `**SasS**`
  
  **Software as a Service**
  
  - 클라이언트 조직의 최종 사용자에게 제공되는 완전한 소프트웨어 솔루션.
    
  - 가장 포괄적인 형식의 클라우드 컴퓨팅 서비스.
    
  - 제공업체가 모든 애플리케이션 관리, 웹 브라우저를 통해 제공.
    
    ex) Google workspace, Dropbox
    

---

# AWS(Amazon Web Services)

**전 세계적으로 분포한 데이터 센터에서 200개가 넘는 완벽한 기능의 서비스를 제공, 세계적으로 가장 포괄적이며 널리 채택되고 있는 클라우드.**

### 핵심 서비스 유형

- `compute`
- `Storage`
- `Database`
- `Networking&Content Delivery`

### AWS 글로벌 인프라

- 데이터 센터 : 일반적으로 수천 대의 서버 수용
- 가용 영역(AZ) : 하나 이상의 데이터 센터
  - 내결합성을 갖도록 설계
- 리전
  - 각 AWS 리전은 두 개 이상의 AZ로 구성.
  - 지속적 성장 중.

### AWS 과금 요소

- compute : 독립 가상 서버 제공하는 경우가 대표적.
- Storage : 각 스토리지 서비스 사용량에 따라 과금.
- Data transfer : 트래픽을 구별하고, 서비스/구간을 분류하여 전송량에 비례해 과금
- Github에 모든 코드를 push하면 AWS Accesskey 같은 민감한 정보 노출 가능.

---

# 실습 관련!

- **`AWS EC2` : Amazon Elastic Compute Cloud**
  
  - AWS에서 확장 가능 컴퓨팅 용량을 제공. → 더 빠르게 애플리케이션 개발 가능.
  - 키페어를 생성한 후 다운받을 때에는 재다운로드 불가 → 안전한 위치에 저장하기!
  - 탄력적 IP는 생성하고 E2C 서버에 연결하지 않으면 비용 발생 주의.
- `Amazon RDS` : Amazon Relational Database Service
  
  - 클라우드에서 관계형 데이터베이스를 간편하게 설정, 운영/확장
  - 비용 효율적, 크기 조정 가능한 용량 제공.
  - 사용자가 애플리케이션에 집중해 애플리케이션에 필요한 빠른 성능, 고가용성, 보안 및 호환성 제공.
  - hostname : RDS 앤드포인트
  - username : RDS 생성 시 입력했던 정보
  - password : RDS 생성 시 입력했던 정보
- 실습 관련 코드
  
  ```jsx
  //EC2 접속
  login as : ubunbu
  
  // JDK 설치(JAVA11 설치)
  sudo apt-get update
  sudo apt-get install openjdk-11-jdk
  
  java -version
  
  // 타임존 변경
  date
  timedetect list-timezone | grep Seoul
  sudo timedetect set-timezone Asia/Seoul
  
  // Hostname 변경
  hostname
  sudo vim /etc/hosts
  
  sudo hostnamectl set-hostname {변경할 이름}
  hostname
  sudo reboot //이후 오류 뜬다. 무시하면 된다.
  
  //EC2 에서 RDS 접속
  sudo apt-get update
  sudo apt-get install mysql-server // ubuntu에서 mysql 설치
  
  mysql -u {username} -p --host(엔드포인트} // mysql 명령어로 접속 시도
  ```
