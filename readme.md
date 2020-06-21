# Toy WebApp Project 01 : keepSD (Keep Social Distancing)
- Azure의 App Services를 사용하여 접속 가능한 webApp을 생성
- URI : http://webapp-keepsd.azurewebsites.net/
- 내용 정리 : [Notion](https://www.notion.so/gentledot/KeepSD-f52e5ad2d79249babb720b03ec5bb83e)

## 구현 목표
- (2020. 06월 기준) 지금까지 학습하고 정리한 내용을 활용하여 필요한 WebApp을 구현할 수 있다.
    - Java 11+ 문법 (함수형 구현) 최대한 활용
    - Spring Core, Spring Web, Spring Security, Spring REST Docs
    - RDB 사용 : DDL, DML (MySQL 기준) 
    - API 문서화 (Swagger UI, Open API 3.0)
    - REST Style API 구현 (Resourceful)
- 유틸성으로 사용할 기능을 정리한다.
- 기획과 방향을 세운 뒤 구현한다. 이 과정에서 UML을 적극 활용한다. (Class-Diagram, ERD 등)
- app 구현과정을 정리하며 제작에 좀 더 익숙해지도록 한다
- git을 통한 형상관리에 익숙해진다. (개발 branch 구성, 명령어 정리 등)

## 테마
- 사회적 거리두기와 관련한 정보를 정리하여 쉽게 접할 수 있도록 한다.
- 다녀갔던 공간을 3밀 (밀폐, 밀집, 밀접)을 기준으로 개인적인 평가와 후기를 기록 할 수 있다.

## 개발 환경
- JDK 11 (OpenJDK 11)
- Spring Boot 2.3.0, Maven Project
- H2 (MySQL mode, In-Memory DB)
- Logback, Junit 5
- Microsoft Azure App Service (container registry)


# 정보 게시물 관리 기능
## table 및 entity 객체 생성
- ksd_board 생성
    ```
    CREATE TABLE ksd_board(
        board_no   bigint PRIMARY KEY AUTO_INCREMENT COMMENT '게시물 번호',
        title      VARCHAR(200)                        NOT NULL COMMENT '게시물 제목',
        content    TEXT                                NOT NULL COMMENT '게시물 내용',
        reference  VARCHAR(250)                        NULL COMMENT '출처 정보',
        created_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
        updated_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
    );
    ```

- 생성된 table의 transaction 처리를 위한 entity 객체 생성
    - Board.class
        ```
        private Long boardNo;               // 게시물 번호
        private String title;               // 게시물 제목
        private String content;             // 게시물 내용
        private String reference;           // 출처 정보
        private LocalDateTime createdAt;    // 등록일시
        private LocalDateTime updatedAt;    // 수정일시
        ```
        - 생성시 제약조건은 test를 통해 정리.
        
## Transaction 구현
### Database 연결 관련 library 의존성 추가
- Spring JDBC와 MyBatis(Spring Boot Starter) 추가
```
org.springframework.boot:spring-boot-starter-jdbc
org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.3
```

### Transaction 확인을 위한 jdbc logging 추가
- 의존성 추가
```
org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16
```

- log4jdbc.log4j2.properties 추가
- logback-spring.xml 추가 (테스트 환경용 logback-test-spring.xml도 추가)
    ```
        <!-- log4jdbc -->
        <logger name="jdbc.sqlonly" level="DEBUG"/>
        <logger name="jdbc.sqltiming" level="DEBUG"/>
        <logger name="jdbc.audit" level="WARN"/>
        <logger name="jdbc.resultset" level="ERROR"/>
        <logger name="jdbc.resultsettable" level="DEBUG"/>
        <logger name="jdbc.connection" level="WARN"/>
    ```


### BoardMapper 구현
- 해당 Mapper의 쿼리는 @Select, @Insert, @Update, @Delete 를 사용하여 작성.
```
    int save(Board board);

    List<Board> findAll(Long pageOffset, Integer contentNumber);

    Board findByBoardNo(Long boardNo);

    int update(Board board);

    int delete(Long boardNo);
```

- Mapper의 동작은 @MyBatisTest를 통해 테스트.

## Service & Controller 구현
- Service의 기능 상세는 BoardServiceTest에 정리.
- BoardController의 기능 요약
    - 게시물 저장: POST /board/insert
    - 게시물 목록 조회 : GET /board/list?page&num
        - page: 게시물 목록 조회 시 시작 offset
        - num: 게시물 목록 조회 시 조회할 게시물 limit
    - 게시물 조회 : GET /board/{boardNo}
    - 게시물 수정 : PUT /board/modify/{boardNo}
    - 게시물 삭제 : DELETE /board/delete/{boardNo}
    
- MVC 동작 테스트는 BoardControllerTest를 통해 실행.
- 추후 통합테스트 진행 후 API Documentation 진행. 
