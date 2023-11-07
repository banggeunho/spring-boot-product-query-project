## Developer

> name : 방근호
contact : panggeunho@gmail.com
>

## Developing Environment

- Java 11 / Gradle / Spring boot 2.7.3
- Macbook Pro (2019), Mackbook M1
- IntelliJ / Github
  
## Used Libraries

- spring-data-jpa : 데이터 ORM
- spring-data-envers : 데이터 변경을 감지하여 이력관리
- h2-database : In-memory DB H2
- lombok
- springdoc-openapi-ui : 간단한 api 검증을 위한 swagger
- opencsv 5.x : CSV Parsing

## Build & Run

```bash
chmod +x /gradlew
./gradlew build
java -jar ./build/libs/*.jar
```

## Swagger & DB Info

```bash
Swagger: http://localhost:8080/swagger-ui/index.html

h2-database: http://localhost:8080/h2-console
 - Driver Class: org.h2.Driver
 - JDBC URL: jdbc:h2:mem:testdb
 - Username : sa
 - password : 없음
```

## Developed Contents

- Open CSV를 이용하여 어플리케이션 실행 시 CSV에서 데이터를 추출하고 DB에 저장
    - 성능 개선을 위해 벌크 삽입 처리
    - JPA의 ID 생성 정책(IDENTITY)에 따른 벌크 처리 제한으로 Spring JDBC Template을 이용하여 벌크 삽입 처리
- Spring-data-envers를 이용하여 상품 정보 이력 관리
- 조회가 자주 발생하는 상품 테이블과 상품 연관 정보 테이블에 item_id에 대해 인덱스 생성
- 상품과 상품 연관 정보 서비스 클래스에 대한 단위 테스트 코드 작성
- 상품 조회 API 구현
    - 기존 JPA를 사용하여 Product의 item_id와 Rec의 target_item_id, result_item_id를 Mapping 하여 조회하였으나, N+1문제와 Lazy Loading 설정이 적용되지 않는 문제가 발생해 쿼리 I/O 최적화를 위해 일부 Mapping 제거 후 구현
    - [프로세스]

        ```jsx
        1. 조회 item_id와 함께 조회 API 호출
        2. 해당 item_id를 통해 Product와 Product_relationship(Rec)을 Join하여 조회
        3. 추출된 result_item_id list를 where in 조건 절을 통해 한 번에 데이터를 조회
        4. 데이터 반환
        ```


- 상품 수정 API 구현 (여러개 상품 수정 가능함)
    - 상품 ID를 제외한 나머지 정보만 수정 가능하도록 제한
    - 기존 수정 요청 정보를 순회하며 데이터를 가져오고, 수정하도록 하였으나 → N개의 요청 데이터가 들어올 경우, 2*N개의 SQL이 생성되기 때문에 최적화 진행
        - 조회 시 itemIdList를 만들어 한 번에 데이터를 가져오도록 수행
        - 업데이트 시 벌크 처리
        - 기존 : **2*N개의 쿼리 발생 → 2개의 쿼리 발생으로 개선**
    - [프로세스]

        ```jsx
        1. 다수의 item_id와 변경할 정보와 함께 수정 API 호출
        2. 해당 item_id를 추출하여 한 번에 Product에서 조회
        3. 요청한 item_id가 있는지 검증
        4. 검증을 통과하면, 수정된 정보를 이용하여 한 번에 업데이트 처리
        ```


- 상품 삭제 API 구현
    - 데이터 삭제 시 is_deleted 컬럼을 true로 변경
    - 해당 작업도 벌크 처리하여 개선 : 2*N개의 쿼리 발생 → 2개의 쿼리 발생
    - [프로세스]

        ```jsx
        1. 다수의 item_id와 함께 삭제 API 호출
        2. 해당 item_id list를 이용하여 한 번에 Product에서 조회
        3. 요청한 item_id가 조회한 데이터에 있는지 검증
        4. 검증을 통과하면, 해당 정보를 이용하여 삭제 처리
        ```

- 상품 입력 API 구현
    - 여러개의 상품 정보 입력 가능
    - 상품 등록 전 상품 존재 여부 확인을 위한 DB 조회 시 벌크 처리
    - Spring Jdbc Template을 이용하여 상품 등록 벌크 처리
    - [프로세스]

        ```jsx
        1. 상품 등록 요청 API 호출
        2. 요청된 상품 ID를 조합하여 In절로 한 번에 조회
        3. 동일한 ID의 상품이 존재하는지 검증
        4. 검증 통과하면 벌크 삽입 처리 
        ```


- 연관 상품 정보 입력 API 구현
    - 등록할 target_item, result_item, score를 요청합니다.
    - target_item_id를 이용하여 상품 정보와 연관된 상품 id를 가져옵니다.
        - JPA N+1 문제를 해결하기 위해 Fetch join을 이용하여 한 번에 가져오도록 처리
    - 연관된 상품 id 정보를 조합하여 한 번에 상품 정보를 전부 가져옵니다.
    - 기해당 정보를 통해 rank를 갱신하고, JPA Dirty checking을 이용하여 update 처리하였습니다.
        - update문이 연관된 상품의 갯수만큼 호출되어, 해당 부분을 벌크 처리 하였습니다.
    - TODO : DB 조회 시 rank를 걸어 주는 것과, 서버에서 데이터를 가져와 rank를 걸어주는 것에 대한 비교 필요
    - [프로세스]

        ```jsx
        1. 상품 정보 입력 API 호출
        2. 요청된 target_id를 통해 상품이 존재하는지 조회 (fetch join)
        3. 요청된 result_id를 통해 상품이 존재하는지 조회
        4. 요청된 target_id, result_id를 통해 연관 정보가 존재하는지 조회
        5. 검증이 모두 통과되면, target_id를 통해 조회한 entity의 연관 상품 정보를 가져옵니다. (1번에서 미리 가져옴)
        6. 해당 정보를 통해 id 리스트를 만들어주고, 한 번에 관련된 상품 정보를 가져옵니다.
        7. score 기준으로 rank를 갱신합니다.
        8. 요청된 연관정보를 저장합니다.
        8. 나머지 변경된 기존 entity들을 list로 만들어 bulk update 합니다.
        ```


- 연관 상품 정보 수정 API 구현
    - Score만 변경 가능하도록 제한하였습니다.
    - [프로세스]

        ```jsx
        1. 상품 정보 입력 API 호출
        2. 요청된 target_id를 통해 상품이 존재하는지 조회 (fetch join)
        3. 요청된 result_id를 통해 상품이 존재하는지 조회
        4. 요청된 target_id, result_id를 통해 연관 정보가 존재하는지 조회
        5. 검증이 모두 통과되면, target_id를 통해 조회한 entity의 연관 상품 정보를 가져옵니다.(1번에서 미리 가져옴)
        6. 해당 정보를 통해 id 리스트를 만들어주고, 한 번에 관련된 상품 정보를 가져옵니다.
        7. score 기준으로 rank를 갱신합니다.
        8. 변경된 entity들을 list로 만들어 bulk update 합니다.
        ```


- 연관 상품 정보 삭제 API 구현
    - is_deleted 컬럼을 두어 데이터는 유지하되, 조회는 되지 않도록 하였습니다.
    - [프로세스]

        ```jsx
        1. 상품 정보 입력 API 호출
        2. 요청된 target_id를 통해 상품이 존재하는지 조회 (fetch join)
        3. 요청된 result_id를 통해 상품이 존재하는지 조회
        4. 요청된 target_id, result_id를 통해 연관 정보가 존재하는지 조회
        5. 검증이 모두 통과되면, 조회한 연관 정보의 is_deleted를 true로 바꿔주고 업데이트합니다.
        6. target_id를 통해 조회한 entity의 연관 상품 정보를 가져옵니다.(1번에서 미리 가져옴)
        7. 삭제된 연관정보를 제외하고 랭킹을 갱신합니다.
        8. 변경된 entity들을 list로 만들어 bulk update 합니다.
        ```
