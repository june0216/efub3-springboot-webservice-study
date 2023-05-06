## RDS란?

AWS에서 지원하는 클라우드 기반 관계형 데이터베이스로 하드웨어 프로비저닝, 데이터베이스 설정, 패치 및 백업과 같이 잦은 운영 작업을 자동화하여 개발자가 개발에 집중할 수 있게 지원하는 서비스

------------
## RDS 인스턴스 생성하기
### 진행순서

1. RDS검색, 선택 후 데이터베이스 생성 클릭
2. 엔진 옵션 선택(MySQL, MariaDB 등)
3. 사용사례(템플릿) 선택(→프리티어)
4. 상세 설정
   + DB 인스턴스 선택시 사용자 이름과 마스터 암호 메모해두기!
5. 네트워크 및 보안
   + 퍼블릭 엑세스 가능 예로 선택

------------
## RDS 운영환경에 맞는 파라미터 설정하기
### 타임존, Character Set, Max Connection 이 세가지를 설정해야한다

### 진행 순서
1. 파라미터 그룹 생성 후 편집 모드로 전환
2. time_zone을 Asia/Seoul로 선택
3. Character Set 변경
   + character_set_client, character_set_connection, character_set_database, character_set_filesytme, character_set_result, character_set_server → utf8mb4로 변경
   + collation_connection, collation_server → utf8mb4_gerneral_ci로 변경
   + utf8과 utf8mb4의 차이: utf8은 이모지를 저장할 수 없지만 utf8mb4은 이모지를 저장할 수 있다.
4. max_connection 150을 변경
   + max_connection은 인스턴스 사양에 따라 자동으로 정해진다. 따라서 바꿔주어야함
5. 생성된 파라미터 그룹을 데이터베이스에 연결
6. DB 파라미터 그룹을 방금 생성한 파라미터 그룹으로 변경(반영 시점: 즉시적용)

------------
## 내 PC에서 RDS로 접속해보기
### 접근을 위해 RDS 보안 그룹에 내 PC의 IP를 추가해야한다.
1. EC2에 사용된 보안 그룹의 그룹 ID복사
2. 복사된 보안그룹 ID와 내 PC의 IP를 RDS 보안 그룹의 인바운드로 추가

------------
## EC2에서 RDS 접근 확인하기
### 진행순서
1. EC2에 ssh 접속(putty 사용)
2. MySQL CLI 설치
   + sudo yum install mysql
3. 계정, 비밀번호, 호스트 주소를 사용해 RDS에 접속
   + mysql -u 계정 -p -h Host주소
4. 패스워드 입력하면 접속 완료!
5. 실제로 생성한 RDS가 맞는지 테스트
   + show database;