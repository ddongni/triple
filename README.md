# 여행지(도시) 관리 API 서버

## 서버 실행 전 환경 설정하기

1. 우선, 해당 서버는 intellij, jdk 11, mysql 환경에서 개발되었으므로 먼저 설치해두어야 한다.

2. 필요한 프로그램을 설치 후 해당 코드를 git clone 받는다. 그러면 triple 폴더가 생성되고 폴더 안에 코드가 들어있는 것을 확인할 수 있다.

   ```
   # git clone https://github.com/ddongni/triple
   ```

2. intellij 에서 clone 받은 triple 폴더를 Spring Gradle 프로젝트로 연다.

3. intellij File > Project Structure 에서 JDK 11 버전을 설정해준다.
    <img width="1229" alt="image" src="https://user-images.githubusercontent.com/68309632/201676005-e994040c-e907-4e2f-8cfe-65b950a5cd1c.png">


## Development 서버

### 실행 방법

1. src > main > resources > application.yml 에서 spring.profile.active 로 "dev"를 입력해준다.
    <img width="1509" alt="image" src="https://user-images.githubusercontent.com/68309632/201674958-660dff8e-88aa-4a8d-8e32-0f129e07b184.png">

2. TripleApplication을 실행(run) 해준다.
    <img width="1508" alt="image" src="https://user-images.githubusercontent.com/68309632/201675669-64f426bb-3c57-42d0-bddf-d1fafdbd0e88.png">

### DB 접속 방법

Development 애플리케이션에 설정한 h2 DB 정보는 다음과 같다. h2 DB 접속시 주의해야 할 점은 url(jdbc:h2:~/triple)을 잘 넣어주었는지 확인해야 한다.

```
- url: jdbc:h2:~/triple
- database name: triple
- username: sa
- password:
```
<img width="1286" alt="image" src="https://user-images.githubusercontent.com/68309632/201678611-950c555c-85b7-42ba-9d80-bedd0b24e0f9.png">


1. 애플리케이션 실행 후, 브라우저에서 localhost:8080/h2-console 에서 h2 DB에 접속한다.

## Production 서버

### 실행 방법

1. src > main > resources > application.yml 에서 spring.profile.active 로 "prod"를 입력해준다.
    <img width="1508" alt="image" src="https://user-images.githubusercontent.com/68309632/201675216-6f794ea0-e6f4-4205-af45-278cf5728ac9.png">

2. TripleApplication을 실행(run) 해준다.
    <img width="1508" alt="image" src="https://user-images.githubusercontent.com/68309632/201675563-b4ff2025-06ae-4aba-926f-c8999a18d32b.png">


### DB 접속 방법

Production 애플리케이션에 설정한 MySQL DB 정보는 다음과 같다.

```
- url: jdbc:mysql://localhost:3306/triple
- database name: triple
- username: dongeun
- password: dongeun
```

1. MySQL 이 설치가 되어 있고 접속할 수 있는 환경에서 커맨드 창을 열고 shell script로 MySQL서버에 접속 한다.

   ```
   # mysql -u root -p
   ```

2. MySQL 서버에 접속 후, "mysql" Database에 접속한다.

   ```
   mysql> use mysql;
   ```

3. 사용자를 추가한다.

   ```
   mysql> create user dongeun@localhost identified by 'dongeun';
   ```

4. 위에서 추가한 사용자의 외부 접근 권한을 부여한다. 애플리케이션에서 MySQL DB 접근 하기 위해서 필요하다.

   ```
   mysql> create user dongeun@'%' identified by 'dongeun';
   mysql> flush privileges;
   ```

5. Database를 생성한다.

   ```
   mysql> create database triple;
   ```

6. triple Database에 접속하여 사용한다. 참고로 TripleApplication을 실행시키면 자동으로 city 테이블과 trip 테이블이 생성된다. 

   ```
   mysql> use triple;
   mysql> show tables;
   ```

   - city 테이블 데이터 조회

     ```
     mysql> select * from city;
     ```

   - trip 테이블 데이터 조회

     ```
     mysql> select * from triple;
     ```

## 여행지(도시) 관리 API 명세서
애플리케이션 실행 후, http://localhost:8080/swagger-ui.html 에 접속하여 서버 API 명세서를 확인하고 API를 요청해볼 수 있다.
<img width="1501" alt="image" src="https://user-images.githubusercontent.com/68309632/201682636-f3fda940-8ab0-4f82-b963-333e376883b9.png">
<img width="1490" alt="image" src="https://user-images.githubusercontent.com/68309632/201682724-5de18bc3-c6e4-44bd-82b6-760926e29adc.png">

