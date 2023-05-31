# chapter8

```solidity
//EC2 프로젝트 Clone 받기
sudo yum install git

git --version

mkdir ~/app && mkdir ~/app/step1

cd ~/app/step1

git clone {복사한 http 주소}

cd {프로젝트명}

./gradlew test

git pull

-bash: ./gradlew: Permission denied

chmod +x ./gradlew

vim ~/app/step1/deploy.sh
```

- vim 에디터만의 특별한 사용법(문법) 있음.
- MariaDB에서 스프링부트 프로젝트를 실행하기 위한 작법
  1. 테이블 생성 : H2에서 자동 생성해주던 테이블들을 MariaDB에선 직접 쿼리를 이용해 생성.
  2. 프로젝트 설정 : 자바 프로젝트가 접근하기 위해서는 데이터베이스 드라이버 필요.
  3. E2C(리눅스) 설정 : 해킹의 위험에 주의!
- JPA가 사용할 테이블은 테스트 코드 수행 시 로그로 생성되는 쿼리를 사용하면 된다.
