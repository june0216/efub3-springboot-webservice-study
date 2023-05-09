# Chapter 7: AWS에 데이터베이스 환경을 만들어보자 - AWS RDS

## RDS 인스턴스 생성하기
검색창에 rds를 입력해서 선택하고, `데이터베이스 생성` 버튼을 클릭한다.
<br>엔진 옵션에서 `MariaDB`를 선택한다.
<br>템플릿에서 `프리 티어`를 선택한다.
<br>스토리지에서 할당된 스토리지를 `20`으로 설정한다.
<br>연결 설정에서 퍼블릭 액세스 가능을 `예`로 설정한다.

## RDS 운영환경에 맞는 파라미터 설정하기
왼쪽 카테고리에서 파라미터 그룹 탭을 클릭한다.
<br>파라미터 그룹 생성 버튼을 클릭한다.
<br>생성한 그룹을 클릭한 후 파라미터 편집 버튼을 누른다.
<br>
- time_zone을 검색하여 Asia/Seoul을 선택한다.
- character 항목들은 모두 utf8mb4로, collation 항목들은 utf8mb4_general_ci로 변경한다.
- max_connection을 150으로 설정한다.
<br>변경 사항 저장 버튼을 클릭해 최종 저장한다.

방금 생성한 데이터베이스를 선택하고 수정 버튼을 누른 후, DB 파라미터 그룹에 방금 생성한 파라미터 그룹을 선택한다.
<br>저장 버튼을 누른 후 수정사항 적용 시점을 즉시 적용으로 선택한다.

## 내 PC에서 RDS에 접속해보기
EC2에 사용된 보안 그룹의 그룹 ID를 복사한 후, 복사된 보안 그룹 ID와 본인의 IP를 RDS 보안 그룹의 인바운드로 추가한다.
<br>인바운드 규칙 유형에서 MYSQL/Aurora를 선택하면 자동으로 3306 포트가 선택된다.

### 인텔리제이에 Database 플러그인을 설치
- DB Browser를 열어 + 버튼을 눌러 MySQL을 선택한다.
- Host에는 RDS의 엔드 포인트를 등록한다.
- Apply > OK 버튼을 눌러 저장한다.
<br>
### 콘솔 실행
- Open SQL Console > New SQL Console... 버튼을 눌러 새로 생성될 콘솔창의 이름을 정한다.
- 생성된 콘솔창에서 SQL을 실행한다.
- `use AWS RDS 데이터베이스명;`
- 쿼리문을 드래그하고 위쪽의 화살표 버튼을 누르면 실행된다.
<br>
### 설정 변경
- 데이터베이스가 선택된 상태에서 현재의 character_set, collation 설정을 확인한다.
`show variables like 'c%';`
- character_set_database, collation_connection 항목은 MariaDB에서만 RDS 파라미터 그룹으로는 변경이 안 된다.
```
ALTER DATABASE 데이터베이스명
CHARACTER SET = 'utf8mb4'
COLLATE = 'utf8mb4_general_ci';
```

### 타임존 확인
- `select @@time_zone, now();`

### 한글 데이터 등록 확인
```sql
CREATE TABLE test (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    content varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

insert into test(content) values ('테스트');

select * from test;
```

## EC2에서 RDS에서 접근 확인
- putty에 접속하여 MySQL CLI 설치<br>
`sudo yum install mysql`
- RDS에 접속<br>
`mysql -u 계정 -p -h Host주소`