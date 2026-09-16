# 🏦 HR Bank (인사 관리 시스템)

> **HR Bank**는 금융·공공 부문 등 기업 환경에 최적화된 고성능 웹 기반 통합 인사 관리 시스템입니다. 부서 및 직원 관리, QueryDSL 기반의 동적 복합 검색 및 커서 페이지네이션, 자동 사번 발급(`EMP-{timestamp}`), 프로필 이미지 파일 관리, 데이터 변경 이력(`ChangeLog` / `Diff`) 추적, 그리고 자동/수동 백업 시스템을 완벽하게 지원합니다.

---

## 🛠️ Tech Stack & Environment

* **Language & Framework**: Java 17, Spring Boot (4.1.1), Spring Data JPA, Spring Batch, Spring WebMVC
* **Query Optimization**: QueryDSL (5.1.0, Jakarta)
* **Database**: PostgreSQL (v15-alpine), H2 Database (Dev/Test)
* **Code Quality & Build**: Gradle, Spotless (Google Java Format v1.17.0), Lombok
* **Configuration**: Spring Dotenv (`me.paulschwarz:spring-dotenv`)
* **Infrastructure**: Docker & Docker Compose

---

## ✨ Core Features & API Endpoints

### 1. 부서 관리 (`Department`)
* **부서 CRUD 및 유니크 제약**: 부서명 중복 방지 및 설립일(`establishedDate`) 관리
* **안전한 삭제 검증**: 소속 직원이 존재하는 부서의 경우 삭제를 방지하는 무결성 보장 로직 탑재

### 2. 직원 관리 및 파일 처리 (`EmployeeController`)
* **직원 등록 (`POST /api/employees`)**: 멀티파트 요청을 통한 프로필 이미지 업로드, 자동 사번 발급(`EMP-{epoch_seconds}`), 요청자 IP 추적 및 생성 이력 기록
* **직원 정보 수정 (`PATCH /api/employees/{id}`)**: 선택적 프로필 이미지 갱신/삭제, 변경 내역 감지 및 Diff 기록
* **직원 삭제 (`DELETE /api/employees/{id}`)**: 직원 데이터 제거 및 연관 파일 정리, 삭제 이력 기록
* **직원 목록 조회 (`GET /api/employees`)**: QueryDSL을 활용한 동적 조건 검색 및 커서 기반 페이징 처리

### 3. 변경 이력 추적 시스템 (`ChangeLogController`)
* **이력 페이징 조회 (`GET /api/change-logs`)**: 시스템 내 발생하는 데이터 변경 이벤트(생성, 수정, 삭제) 이력의 커서 기반 조회 지원
* **기간별 변경 건수 집계 (`GET /api/change-logs/count`)**: 특정 기간(`fromDate` ~ `toDate`) 동안 발생한 변경 로그 건수 확인
* **상세 이력 조회 (`GET /api/change-logs/{id}`)**: 특정 변경 로그에 매핑된 상세 필드 변경 전후 값(`Diff`) 추적

### 4. 백업 및 시스템 관리 (`BackupController`)
* **자동/수동 백업 기능**: 변경 이력(`ChangeLog`) 발생 여부에 따른 스마트 백업 및 주기적 실행(`backup.schedule.fixed-delay`) 지원
* **최신 백업 조회 (`GET /api/backups/latest`)**: 상태별(`BackupStatus`) 가장 최근에 종료된 백업 정보 및 파일 ID 연동 조회

---

## ⚙️ Configuration & Environment Variables (.env)

프로젝트 루트에 `.env` 파일을 생성하여 데이터베이스 접속 정보를 설정해야 합니다. (`application.properties`는 환경 변수를 안전하게 로드합니다.)

```properties
POSTGRES_PORT=5432
POSTGRES_DB=hrbank
POSTGRES_USER=your_username
POSTGRES_PASSWORD=your_password