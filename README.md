# 프리온보딩 백엔드 인턴십 선발과제
### :white_check_mark: 서비스 소개
* 본 서비스는 기업의 채용을 위한 웹 서비스입니다.
* 회사는 채용공고를 생성하고, 사용자는 원하는 채용에 지원할 수 있습니다.

  <br>

## :wrench: 기술 스택
![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white)
![SpringBoot](https://img.shields.io/badge/Springboot-%236DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Hibernate](https://img.shields.io/badge/JPA/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![QueryDsl](https://img.shields.io/badge/QueryDsl-000000?style=for-the-badge&logo=QueryDsl&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

<br>

## :wrench: 구조 및 설계
### ERD
![erd](https://github.com/user-attachments/assets/734716bc-d2fb-4ba2-870c-63f7c00f0fb4)

<br>

## :wrench: 구현 기능
### :white_check_mark: 채용공고 등록
* 사용자가 회사와 직무 정보를 제공하여 새로운 채용공고를 시스템에 저장
* 존재하지 않는 companyId는 예외를 발생

### :white_check_mark: 채용공고 수정
* 기존 채용공고를 업데이트하여 새로운 정보를 저장
* 존재하지 않는 jobPostId는 예외를 발생

### :white_check_mark: 채용공고 삭제
* 특정 jobPostId를 가진 채용공고를 데이터베이스에서 제거
* 삭제 요청 시, 해당 채용공고가 존재하는지 확인한 후 삭제 작업을 수행

### :white_check_mark: 채용공고 목록
* 모든 채용공고를 목록 형태로 반환
* 각 채용공고의 일부를 포함한 DTO를 생성하여 제공

### :white_check_mark:채용공고 상세
* 특정 채용공고의 세부 정보를 제공
* 해당 채용공고와 동일한 회사의 다른 채용공고 ID 목록도 함께 제공

### :white_check_mark:채용공고 검색
* 키워드를 기반으로 채용공고를 필터링
* 각 공고의 위치, 보상, 기술 스택 등이 포함된 목록을 제공

### :white_check_mark: 채용공고 지원
* 사용자가 특정 채용공고에 지원 가능
* 사용자가 해당 공고에 이미 지원했는지 확인하고, 중복 지원이 아닐 경우에만 지원 정보를 저장

<br>

## :wrench: 유닛 테스트
* JUnit 5와 Mockito를 사용하여 유닛 테스트를 구현
* `JobPostingService`, `ApplyService` 유닛 테스트 구현

![유닛테스트](https://github.com/user-attachments/assets/03686d98-2bd4-43d3-8ba6-e134e79115da)

<br>

### :wrench: 디렉토리 구조
```
🗂️ src
├─ 🗂️ main
│  ├─ 🗂️ java
│  │  └─ 🗂️ com/wanted/wantedpreonboardingbackend
│  │     ├─ 📂 domain
│  │     │  ├─ 📂 company
│  │     │  │  ├─ 📁 entity
│  │     │  │  └─ 📁 repository
│  │     │  ├─ 📂 jobApplication
│  │     │  │  ├─ 📁 controller
│  │     │  │  ├─ 📁 dto
│  │     │  │  ├─ 📁 entity
│  │     │  │  ├─ 📁 repository
│  │     │  │  └─ 📁 service
│  │     │  ├─ 📂 jobPosting
│  │     │  │  ├─ 📁 controller
│  │     │  │  ├─ 📁 dto
│  │     │  │  ├─ 📁 entity
│  │     │  │  ├─ 📁 repository
│  │     │  │  └─ 📁 service
│  │     │  └─ 📂 user
│  │     │     ├─ 📁 controller
│  │     │     ├─ 📁 dto
│  │     │     ├─ 📁 entity
│  │     │     ├─ 📁 repository
│  │     │     └─ 📁 service
│  │     └─ 📂 global
│  │         └─ 📂 exception
│  │             ├─ 📁 advice
│  └─ 📂 resources
└─ 📂 test
    └─ 🗂️ java
        └─ 🗂️ com/wanted/wantedpreonboardingbackend
            ├─ 📂 domain
            │  ├─ 📂 jobApplication
            │  └─ 📂 jobPosting
            └─ 📁 WantedPreOnboardingBackendApplicationTests.java

```
