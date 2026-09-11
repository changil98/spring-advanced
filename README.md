# 입문 주차 프로젝트 (crimson-citadel)

**<details><summary> Lv 1. 설정 파일 작성: Docker MySQL 연결**
</summary>

- [Lv 1. 설정 파일 작성: Docker MySQL 연결](https://github.com/changil98/spring-advanced/blob/main/src/main/resources/application.properties)   
    
- Docker를 사용해서 MySQL 연결
    - crimson_citadel DB 생성
</details>

---

**<details><summary>Lv 2. 의존성 주입(DI)**
</summary>

- 오류 발생
    
- GameService 빈 등록
    
    
- 서버 정상 작동 및 MySQL 테이블 생성

</details>

---

**<details><summary>Lv 3. RESTful API: 게임 목록 조회**
</summary>

- `“/game”` → `“/games”`로 변경
    

    
- 요청 `200`과 빈 목록(`[]`)을 반환

    
- 게임 타이틀이 에러 없이 오픈
    - 기존에 발생하던 오류

      <img width="599" height="73" alt="image (14)" src="https://github.com/user-attachments/assets/cffec869-9091-4937-ad42-0a3592e3af69" />

    - 오류 없이 페이지 오픈
</details>

---

**<details><summary>Lv 4. @Transactional**
</summary>

- `@Transactional(readOnly = true)` → `@Transactional()`로 변경
    

    
- `서버 응답이 API 명세와 다릅니다 (deck[0].id)` 발동 체크

<img width="483" height="67" alt="image (13)" src="https://github.com/user-attachments/assets/06eef344-4eb8-4ff6-b225-eb7fde7828ba" />


</details>

---

**<details><summary>Lv 5. Bean Validation: 게임 생성**
</summary>

- [`CardResponse`](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/runcard/dto/CardResponse.java)   

- [`RunCardRequest`](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/runcard/dto/RunCardRequest.java)
    
- 시작 보상 화면까지 이동 → 카드 선택 오류 발생

<img width="619" height="70" alt="image (12)" src="https://github.com/user-attachments/assets/80a14ebc-914f-4d80-84a5-f2be57b8da18" />

    
</details>

---

**<details><summary>Lv 6. 보상 카드 선택과 진행 저장**
</summary>

- [`updateProgress` 주석 풀고 수정 (`ResponseEntity<?>`에서 ? 변경)](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/controller/GameController.java)
    
- 시작 보상 선택 후 전투 진입 확인
    
</details>

---

**<details><summary>Lv 7. 보상 카드 선택과 진행 저장**
</summary>

- 게임 목록 조회 API

  -  [`GameSummaryResponse` 생성](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/dto/GameSummaryResponse.java)

  - [`GameController` → `getGaems` 수정](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/controller/GameController.java)

  - [`GameService` → `getGames` 수정](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/service/GameService.java)

- 게임 상세 조회 API

    - [`GameController` → `getGaem` 수정](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/controller/GameController.java)
    
    - [`GameService` → `getGaem` 수정](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/service/GameService.java)

- “저장 된 여정”에 id기준 내림차순으로 표시 , 선택하면 저장된 HP·층·덱 그대로 이어짐
    - 방식 : 이름(밤의 후계자) 생성 및 보상 선택 X → 재시작 → 이름(어둠) 보상 선택 O → 재시작 → 저장된 데이터 확인 → 이름(어둠)으로 다시 시작 → 체력 및 키드 갯수 확인

</details>


---

**<details><summary>Lv 8.  더티 체킹: 이름 수정, 자식부터 삭제**
</summary>

- 플레이어 이름 변경
  - [RenameRequest 생성](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/dto/RenameRequest.java) 

  - [GameController → renameGame 수정](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/controller/GameController.java)

  - [GameService → renameGame 수정](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/service/GameService.java)
- 게임 삭제

  - [GameController → deleteGame 수정](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/controller/GameController.java)

  - [GameService → deleteGame 수정](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/service/GameService.java)
</details>

---

**<details><summary>Lv 9. 끝난 게임 덮어쓰기 막기**
</summary>

- `ResponseStatusException`으로 409 반환하고 데이터 변경 X

</details>

---

**<details><summary>Lv 10. 전역 예외 처리: 404·409에 message 붙이기**
</summary>

- [`GlobalExceptionHandler`에 `GameNotFoundException` → 404, `GameFinishedException` →409로 바꾸는 핸들러 추가](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/common/exception/GlobalExceptionHandler.java)

</details>

---

**<details><summary>Lv 11. N+1 없는 카드 수 집계와 저장 시간**
</summary>

- N + 1 없는 카드 수 집계

  - [DeckCount DTO 클래스 생성](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/runcard/dto/DeckCount.java)

     - 요구 사항: 게임 ID와 카드 수를 담는 DTO 클래스.

  
  - [@Query 작성](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/runcard/repository/RunCardRepository.java)

     - 요구 사항: select new 패키지 구문으로 group by 결과를 dto 목록으로 바로 조회


  - [적용](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/service/GameService.java)

- 저장 시간

  - @EnableJpaAuditing 적용
    

    
  - [BaseEntity 클래스 생성](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/game/entity/BaseEntity.java)
    
    ※ JPA에서 사용하던 @Temporal 어노테이션은 Jakarta Persistence 3.2 버전 부터 공식적으로 지원 중단 ※
    
  - Game에 적용

    
  - 목록·상세 응답에 적용
    - `GameDetailResponse`, `GameSummaryResponse`에 필드, 생성자 추가
    - `GameService` → `createGame`, `getGames`, `getGame`, `updateProgress` 에 적용
  - 결과
    
    <img width="747" height="494" alt="image (8)" src="https://github.com/user-attachments/assets/04e2e88b-0fe5-4fd5-bd9d-6b76b2eafb46" />

</details>

---

**<details><summary>Lv 12. 랭킹**
</summary>

- [`RankingService`](https://github.com/changil98/spring-advanced/blob/main/src/main/java/com/gamebasic/ranking/service/RankingService.java)
-  외부 랭킹 API
    -  `RestClient` 는 스프링이 제공하는 HTTP 클라이언트로, `retrieve().body(클래스)`가 응답 JSON을 그 클래스의 객체로 바꿔 줍니다.

    - 요청 본문을 받을 때와 같은 방식이라 JSON 필드 이름과 클래스 필드 이름이 같아야합니다.

    - 외부 랭킹 API의 응답을 받을 DTO 클래스들을 **랭킹 API** 형식에 맞게 만들었습니다.

- 순위 대상

  - run.status가 CLEARED 이고 run.clearedFloor가 10인 기록만 순위 대상입니다.  (excludedCount에 들어가지 않습니다.)

- 정상 기록 조건

  - 순위 대상 중 표에서 하나라도 어기는 기록은 이상 기록으로 보고 제외합니다. (excludedCount 증가.)

- 정렬

  - 클리어 타임을 오름차순 → 같은 값이 있다면 남은 HP로 내림차순 → 그대로 같은 값이 있다면 id를 오름차순으로 정렬합니다.

- 플레이어당 하나
  
  - 같은 Payer.id의 기록이 여러 개면 정렬 순서에서 앞선 하나만 남깁니다.  (AI 활용했음)

- 확인

저장된 여정 화면 아래에 “랭킹” 패널이 나타나고 1위부터 3위까지 표시됩니다. 응답이 명세와 다르거나 수누이가 틀리면 게임은 에러 창을 띄우고 시작되지 않습니다. 필드나 타입이 다르면 에러 창에 그 필드 이름이 나오고, 순위가 틀리면 “랭킹 결과가 기대한 순위와 다릅니다.”라고만 나오므로 위 규칙을 하나씩 다시 확인합니다.
<img width="1372" height="813" alt="image (7)" src="https://github.com/user-attachments/assets/4288356c-c2df-4dee-b086-7f7d1a8d9587" />

    
</details>

---


