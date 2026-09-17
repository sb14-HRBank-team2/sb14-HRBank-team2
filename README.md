# 🏦 HR Bank (인사 관리 시스템)

> **HR Bank**는 금융·공공 부문 등 기업 환경에 최적화된 고성능 웹 기반 통합 인사 관리 시스템입니다. 부서 및 직원 관리, QueryDSL 기반의 동적 복합 검색 및
> 커서 페이지네이션, 자동 사번 발급 (`EMP-{timestamp}`), 프로필 이미지 파일 관리, 데이터 변경 이력 (`ChangeLog` / `Diff`) 추적, 그리고
> 자동/수동 백업 시스템을 완벽하게 지원합니다.

---

## 🧑‍💻 팀원 구성

|                   박태양                    |                   김어진                    |                     신상엽                      |                 박성욱                  |                조예준                 |               정수환                |
|:-------------------------------------------:|:-------------------------------------------:|:-----------------------------------------------:|:---------------------------------------:|:-------------------------------------:|:-----------------------------------:|
|                    팀장                     |                    팀원                     |                      팀원                       |                  팀원                   |                 팀원                  |                팀원                 |
| [parksunovo](https://github.com/parksunovo) | [rladjwls02](https://github.com/rladjwls02) | [   Smil-limS   ](https://github.com/Smil-limS) | [ziezz767](https://github.com/ziezz767) | [joyejun](https://github.com/joyejun) | [swanhw](https://github.com/swanhw) |

---

## 🛠️ Tech Stack & Environment

* **Language & Framework**: Java 17, Spring Boot (4.1.1), Spring Data JPA, Spring Batch, Spring
  WebMVC
* **Query Optimization**: QueryDSL (5.1.0, Jakarta)
* **Database**: PostgreSQL (v15-alpine), H2 Database (Dev/Test)
* **Code Quality & Build**: Gradle, Spotless (Google Java Format v1.17.0), Lombok
* **Configuration**: Spring Dotenv (`me.paulschwarz:spring-dotenv`)
* **Infrastructure**: Docker & Docker Compose

---

## 📁 패키지 구조

```text
com/sprint/hrbank/
├── HrbankApplication.java             # Spring Boot 시작점
├── domain/                            # 핵심 데이터와 상태
│   ├── employee/                      # Employee, EmployeeStatus
│   ├── department/                    # Department
│   ├── chagelog/                      # ChangeLog, Diff, ChangeType
│   ├── backup/                        # Backup, BackupStatus
│   └── fileinfo/                      # FileInfo
├── application/                       # 유스케이스와 도메인 간 협력
│   ├── facade/
│   │   └── EmployeeAppService.java    # 직원 변경 시 부서·파일·이력을 조합
│   ├── employee/
│   │   ├── EmployeeCommandService.java
│   │   ├── EmployeeQueryService.java
│   │   ├── EmployeeStatisticsQueryService.java
│   │   ├── provided/                  # 외부에 제공하는 직원 기능 인터페이스
│   │   │   ├── command/               # 생성·수정·삭제 계약
│   │   │   └── query/                 # 조회·검색·집계 계약
│   │   ├── required/                  # 직원 저장·조회 Repository
│   │   ├── dto/                       # 직원 요청·응답·페이지 DTO
│   │   └── validation/                # 직원 검색 조건 검증
│   ├── department/                    # 부서 변경·조회 서비스 및 포트·DTO
│   ├── changelog/                     # 이력 변경·조회, Diff 및 포트·DTO
│   ├── backup/                        # 백업, CSV/로그 생성, 스케줄러 및 포트·DTO
│   └── fileinfo/                      # 파일 업로드·다운로드·삭제, 메타데이터 저장
├── adapter/                           # HTTP와 영속성의 외부 접점
│   ├── webapi/                        # 직원·부서·이력·백업·파일 Controller
│   │   ├── employee/
│   │   ├── department/
│   │   ├── changelog/
│   │   ├── backup/
│   │   └── fileinfo/
│   └── persistence/                   # QueryDSL 동적 검색 구현과 검색 조건
│       ├── employee/
│       ├── department/
│       ├── changelog/
│       └── backup/
└── common/                            # 공통 기술 설정·예외
    ├── config/                        # JPA, QueryDSL 설정
    ├── advice/                        # 전역 예외 처리
    └── exception/                     # 공통 예외 타입·응답
```

### 주요 책임과 요청 흐름

- `domain`은 JPA 엔티티와 상태를 담습니다. `Employee`와 `Department` 등의 데이터와 상태 변경은 각 도메인 객체에서 관리합니다.
- `adapter.webapi`의 Controller는 HTTP 요청을 받고 `application.*.provided` 인터페이스를 호출한 뒤 결과를 응답합니다.
- `application.*.provided`는 애플리케이션이 제공하는 기능의 계약입니다. `command`는 생성·수정·삭제, `query`는 조회·검색·집계 기능을
  구분합니다.
- `application.*Service`는 해당 도메인의 유스케이스를 구현합니다. 특히 `application.facade.EmployeeAppService`는 직원
  등록·수정·삭제에서 부서 조회, 파일 처리, 직원 변경, 변경 이력 기록을 한 유스케이스로 조합하고 DB 작업에 트랜잭션을 적용합니다.
- `application.*.required`에는 저장·조회에 필요한 Repository 인터페이스가 있습니다. 현재 구현에서는 이 인터페이스가 Spring Data JPA를
  상속하고, QueryDSL 검색은 `adapter.persistence`의 사용자 정의 Repository 구현을 연결해 사용합니다.
- `application.*.dto`는 API 입력·출력에 필요한 데이터를 담고, `common`은 여러 기능에서 사용하는 설정과 예외 처리를 담당합니다.

예를 들어 직원 목록 조회는
`EmployeeController → EmployeePageMaker → EmployeeQueryService → EmployeeRepository/EmployeeQRepositoryImpl → DB`
순서로 처리합니다. 직원 등록은 `EmployeeController → EmployeeRegister → EmployeeAppService`를 거쳐 직원 저장, 프로필 파일 처리,
변경 이력 기록을 함께 수행합니다.

> `domain/chagelog`는 현재 소스의 실제 패키지 이름입니다. 패키지명을 수정할 때에는 관련 import와 참조도 함께 변경해야 합니다.

---

## ✨ Core Features & API Endpoints

### 1. 부서 관리 (`Department`)

* **부서 CRUD 및 유니크 제약**: 부서명 중복 방지 및 설립일 (`establishedDate`) 관리
* **안전한 삭제 검증**: 소속 직원이 존재하는 부서의 경우 삭제를 방지하는 무결성 보장 로직 탑재

### 2. 직원 관리 및 파일 처리 (`EmployeeController`)

* **직원 등록 (`POST /api/employees`)**: 멀티파트 요청을 통한 프로필 이미지 업로드, 자동 사번 발급 (`EMP-{epoch_seconds}`), 요청자
  IP 추적 및 생성 이력 기록
* **직원 정보 수정 (`PATCH /api/employees/{id}`)**: 선택적 프로필 이미지 갱신/삭제, 변경 내역 감지 및 Diff 기록
* **직원 삭제 (`DELETE /api/employees/{id}`)**: 직원 데이터 제거 및 연관 파일 정리, 삭제 이력 기록
* **직원 목록 조회 (`GET /api/employees`)**: QueryDSL을 활용한 동적 조건 검색 및 커서 기반 페이징 처리

### 3. 변경 이력 추적 시스템 (`ChangeLogController`)

* **이력 페이징 조회 (`GET /api/change-logs`)**: 시스템 내 발생하는 데이터 변경 이벤트 (생성, 수정, 삭제) 이력의 커서 기반 조회 지원
* **기간별 변경 건수 집계 (`GET /api/change-logs/count`)**: 특정 기간 (`fromDate` ~ `toDate`) 동안 발생한 변경 로그 건수 확인
* **상세 이력 조회 (`GET /api/change-logs/{id}`)**: 특정 변경 로그에 매핑된 상세 필드 변경 전후 값 (`Diff`) 추적

### 4. 백업 및 시스템 관리 (`BackupController`)

* **자동/수동 백업 기능**: 변경 이력 (`ChangeLog`) 발생 여부에 따른 스마트 백업 및 주기적 실행 (`backup.schedule.fixed-delay`) 지원
* **최신 백업 조회 (`GET /api/backups/latest`)**: 상태별 (`BackupStatus`) 가장 최근에 종료된 백업 정보 및 파일 ID 연동 조회

---

## ⚙️ Configuration & Environment Variables (.env)

프로젝트 루트에 `.env` 파일을 생성하여 데이터베이스 접속 정보를 설정해야 합니다. (`application.properties`는 환경 변수를 안전하게 로드합니다.)

```properties
POSTGRES_PORT=5432
POSTGRES_DB=hrbank
POSTGRES_USER=your_username
POSTGRES_PASSWORD=your_password
```

---

## 🌐 HRBank 링크

## [ HRBank-turtles](https://sb14-hrbank-turtles-production.up.railway.app/#/dashboard) 