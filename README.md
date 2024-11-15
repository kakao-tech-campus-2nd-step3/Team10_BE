# Team10_BE

![배너 사진](docs/banner.png)
<p align="center">농산물로 지역을 잇는 플랫폼 “품앗이” 의 백엔드 서버입니다.</p>
<p align="center">
    <a href="https://www.kakaotechcampus.com/user/index.do" target="_blank">
        카카오 테크 캠퍼스 2기
    </a> 부산대 10조 프로젝트입니다.
</p>

## 📑 목차

- [📌 프로젝트 소개](#프로젝트-소개)
- [🛠️ 기술 스택](#기술-스택)
    - [Backend](#Backend)
    - [Build & Database](#Build-&-Database)
    - [Cloud & Deployment](#Cloud-&-Deployment)
- [📂 프로젝트 구조](#프로젝트-구조)
- [📄 API 명세서](#API-명세서)
- [📊 ERD](#ERD)
- [🚀 프로젝트 실행 방법](#프로젝트-실행-방법)
- [🔒 보안 설정](#보안-설정)
- [💳 결제 시스템 설정](#결제-시스템-설정)
- [🌾 도메인 설명](#도메인-설명)
    - [농장 도메인](#농장-도메인)
    - [상품 도메인](#상품-도메인)
- [🔄 지속적인 통합 및 배포](#지속적인-통합-및-배포)
    - [배포 개요](#배포-개요)
    - [배포 프로세스](#배포-프로세스)
    - [알림 및 모니터링](#알림-및-모니터링)
- [👥 Collaborators](#Collaborators)

## 📌 프로젝트 소개

품앗이는 농민과 소비자를 직접 연결하는 `온라인 직거래 플랫폼`입니다. 경매 법인의 독점과 과도한 유통비용으로 인한 기존 도매시장의 문제를 해결하고자, 농산물 유통 과정을 간소화하여 중소 농민들이 정당한 가격을 받을
수 있도록 지원합니다. 직관적인 UI와 신뢰성 높은 프로필 정보 제공을 통해, 소비자는 합리적인 가격에 고품질 농산물을 구매할 수 있습니다.

## 🛠️ 기술 스택

<div align="center">

### Backend

<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white" />
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white" />
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white" />
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white" />
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=Spring Data JPA&logoColor=white" />
<img src="https://img.shields.io/badge/JPA-007396?style=flat-square&logo=Java&logoColor=white" />
<img src="https://img.shields.io/badge/JWT-000000?style=flat-square&logo=JSON Web Tokens&logoColor=white" />
<img src="https://img.shields.io/badge/Kakao oauth2.0-FFCD00?style=flat-square&logo=Kakao&logoColor=white" />

### Build & Database

<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white" />
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white" />
<img src="https://img.shields.io/badge/RDS-527FFF?style=flat-square&logo=Amazon RDS&logoColor=white" />

### Test

<img src="https://img.shields.io/badge/JUnit5-25A162?style=flat-square&logo=JUnit5&logoColor=white" />
<img src="https://img.shields.io/badge/Mockito-DA383E?style=flat-square&logo=Mockito&logoColor=white" />

### Cloud & Deployment

<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white" />
<img src="https://img.shields.io/badge/Amazon ECS-FF9900?style=flat-square&logo=Amazon ECS&logoColor=white" />
<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white" />
<img src="https://img.shields.io/badge/ELB-FF9900?style=flat-square&logo=Amazon AWS&logoColor=white" />
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat-square&logo=Amazon S3&logoColor=white" />

### AI & OCR

<img src="https://img.shields.io/badge/Naver OCR-03C75A?style=flat-square&logo=Naver&logoColor=white" />

</div>

<details>
<summary>버전 정보</summary>

- **Gradle JVM**: 22.0.2
- **Spring Boot**: 3.3.1
- **Java**: 21
- **MySQL**: 8.0

</details>

## 📂프로젝트 구성

```
.
├── build
│   ├── classes
│   ├── generated
│   ├── reports
│   └── resources
├── gradle
│   └── wrapper
└── src
    ├── main
    │   ├── java
    │   │   └── poomasi
    │   │       ├── Application.java
    │   │       ├── domain
    │   │       │   ├── auth
    │   │       │   │   ├── security
    │   │       │   │   │   ├── filter
    │   │       │   │   │   ├── handler
    │   │       │   │   │   └── oauth2
    │   │       │   │   ├── signup
    │   │       │   │   └── token
    │   │       │   ├── farm
    │   │       │   │   ├── _category
    │   │       │   │   └── _schedule
    │   │       │   ├── member
    │   │       │   │   ├── _biz
    │   │       │   │   └── _profile
    │   │       │   ├── order
    │   │       │   │   └── _aftersales
    │   │       │   ├── product
    │   │       │   │   ├── _cart
    │   │       │   │   ├── _category
    │   │       │   │   └── _intro
    │   │       │   ├── reservation
    │   │       │   ├── review
    │   │       │   │   ├── farm
    │   │       │   │   └── product
    │   │       │   └── wishlist
    │   │       ├── global
    │   │       │   ├── common
    │   │       │   ├── health
    │   │       │   ├── ocr
    │   │       │   └── util
    │   │       └── payment
    │   └── resources
    └── test
        ├── java
        │   └── poomasi
        │       ├── domain
        │       └── global
        └── resources
```

## 📄 API 명세서

[배포용 품앗이 명세서](https://bubble-pick-143.notion.site/1e48cc52884d4df993857a1e8f58ff26?pvs=4)

## 📊 ERD 요약

| 항목    | 설명               | ERD 이미지                         |
|-------|------------------|---------------------------------|
| 회원    | 회원의 기본 정보와 관계    | ![회원 ERD](docs/member-erd.png)  |
| 상품    | 상품 관련 정보와 카테고리   | ![상품 ERD](docs/product-erd.png) |
| 장바구니  | 상품과 사용자의 연관 관계   | ![장바구니](docs/cart-erd.png)      |
| 농장    | 농장 정보와 예약 시스템    | ![농장 ERD](docs/farm-erd.png)    |
| 위시리스트 | 사용자의 관심 상품 저장 정보 | ![위시리스트](docs/wishlist-erd.png) |

## 🚀 프로젝트 실행 방법

1. 프로젝트를 클론하고 디렉토리로 이동합니다.

```bash
git clone
cd Team10_BE
```

2. `application-secret.yml` 파일을 프로젝트 루트에 생성하고, 다음과 같은 내용을 추가합니다.

```yaml
spring:
  application:
    name: poomasi
  jpa:
    open-in-view: false
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        enable_lazy_load_no_trans: true
        hbm2ddl:
          jdbc_metadata_extraction_strategy: individually
  datasource:
    url: jdbc:mysql://localhost:3306/poomasi
    username: root
    password: <DB_PASSWORD>
    driver-class-name: com.mysql.cj.jdbc.Driver

  data:
    redis:
      port: 6379
      host: <REDIS_HOST>

  security:
    redirect_url: http://localhost:3000
    oauth2:
      client:
        registration:
          kakao:
            client-id: <KAKAO_CLIENT_ID>
            client-secret: <KAKAO_CLIENT_SECRET>
            scope: account_email, profile_nickname
            client-name: Kakao
            authorization-grant-type: authorization_code
            client-authentication-method: client_secret_post
            redirect-uri: http://localhost:8080/login/oauth2/code/kakao
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: kakao_account

logging:
  level:
    org:
      springframework:
        web: DEBUG

jwt:
  secret: <JWT_SECRET>
  access-token-expiration-time: 3600000  # 1시간
  refresh-token-expiration-time: 604800000  # 7일

aws:
  s3:
    bucket: poomasi
    region: ap-northeast-2
  access: <AWS_ACCESS_KEY>
  secret: <AWS_SECRET_KEY>

imp:
  api:
    key: <IMP_API_KEY>
    secretKey: <IMP_SECRET_KEY>

naver:
  ocr:
    secret: <NAVER_OCR_SECRET>
    invoke: <NAVER_OCR_INVOKE_URL>
    template: <NAVER_OCR_TEMPLATE_ID>
```

3. application-secret.yml 파일을 저장한 후, 프로젝트를 실행합니다.

```
./gradlew bootRun
```

## 🌾 Feature

> 개발한 API들의 핵심 특성을 서술합니다.

### 상품 도메인

### 농장 도메인

농장 도메인은 농장 정보를 관리하는 도메인입니다. 농장 정보는 농장 이름, 농장 소개, 농장 위치, 농장 사진, 농장 카테고리, 농장 스케줄로 구성되어 있습니다.

#### 1. 농장 사업자 등록

> 농장 사업자 등록은 농장을 등록하는 기능입니다.

#### 2. 농장 체험 예약

> 농장 체험 예약은 농장에서 진행하는 체험 프로그램을 예약하는 기능입니다.

농장 체험 예약은 농장 체험 예약 정보, 농장 체험 예약 상태, 농장 체험 예약 인원으로 구성되어 있습니다.

- **농장 체험 예약 정보**
    - 농장 체험 예약 정보는 농장 체험 예약을 식별하는 기본키입니다.
    - 농장 체험 예약 정보는 농장 체험 예약 상태, 농장 체험 예약 인원, 농장 체험 예약 날짜로 구성되어 있습니다.
- **농장 체험 예약 상태**
    - 농장 체험 예약 상태는 농장 체험 예약의 상태를 나타냅니다.
    - 농장 체험 예약 상태는 예약 완료, 예약 취소, 예약 대기 중으로 구성되어 있습니다.
- **농장 체험 예약 인원**
    - 농장 체험 예약 인원은 농장 체험 예약을 신청한 인원 수를 나타냅니다.
    - 농장 체험 예약 인원은 1명 이상으로 구성되어 있습니다.
- **농장 체험 예약 날짜**
    - 농장 체험 예약 날짜는 농장 체험 예약을 신청한 날짜를 나타냅니다.
    - 농장 체험 예약 날짜는 농장 체험 예약을 신청한 날짜로 구성되어 있습니다.
- **농장 체험 예약 취소**
    - 농장 체험 예약 취소는 농장 체험 예약을 취소하는 기능입니다.
    - 농장 체험 예약 취소는 농장 체험 예약을 취소하는 기능으로 구성되어 있습니다.
- **농장 체험 예약 완료**
    - 농장 체험 예약 완료는 농장 체험 예약을 완료하는 기능입니다.
    - 농장 체험 예약 완료는 농장 체험 예약을 완료하는 기능으로 구성되어 있습니다.
- **농장 체험 예약 대기 중**
    - 농장 체험 예약 대기 중은 농장 체험 예약을 대기 중인 상태를 나타냅니다.
    - 농장 체험 예약 대기 중은 농장 체험 예약을 대기 중인 상태로 구성되어 있습니다.
- **농장 체험 예약 인원**
    - 농장 체험 예약 인원은 농장 체험 예약을 신청한 인원 수를 나타냅니다.
    - 농장 체험 예약 인원은 1명 이상으로 구성되어 있습니다.

#### 3. 농장 리뷰 조회

## 🔒 Security 설정


## 💳 결제 시스템

![포트원 API 결제 프로세스](docs/payment-process.png)

## 🔄 지속적인 통합 및 배포

**배포 개요**

본 프로젝트는 GitHub Actions를 활용하여 ECS 및 EC2 환경에 자동으로 배포됩니다. Bridge 네트워크 모드로 Docker 컨테이너를 구성하여, ELB를 통해 외부 요청을 안정적으로 분산 처리할 수
있도록 설정되어 있습니다.
![배포 시나리오](docs/deploy.png)
**배포 프로세스**

	1.	코드 변경 및 트리거: prod 브랜치에 코드가 푸시될 때마다 GitHub Actions가 자동으로 배포 프로세스를 시작합니다.
	2.	빌드 및 테스트: Gradle을 통해 프로젝트를 빌드하고, Docker 이미지를 생성합니다.
	3.	Amazon ECR에 이미지 저장: 생성된 Docker 이미지를 Amazon ECR에 푸시하여 이미지의 버전 관리를 수행합니다.
	4.	ECS에 배포 및 업데이트: 새로운 Docker 이미지가 ECS Task Definition에 반영되어 ECS에서 새로운 컨테이너를 자동으로 시작합니다.
	5.	ELB를 통한 로드 밸런싱: ELB(Elastic Load Balancer)를 사용하여 외부 요청을 분산 처리하고, 높은 트래픽을 안정적으로 관리할 수 있도록 설정합니다.

**알림 및 모니터링**

	Slack 알림: 빌드 및 배포 상태는 Slack 채널로 알림이 전송되며, 성공 및 실패 여부에 따라 적절한 알림 메시지가 전송됩니다. 이를 통해 실시간으로 배포 상태를 파악할 수 있습니다.

## 👥 Collaborators

<h3 align="center"> Backend</h3>
<div align="center">
<table align="center">
  <tr>
    <td align="center" width="200px">
      <a href="https://github.com/amm0124" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/108533909?v=4" width="100px" alt="김건호 프로필" />
      </a>
    </td>
    <td align="center" width="200px">
      <a href="https://github.com/canyos" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/31244128?v=4" width="100px" alt="이풍헌 프로필" />
      </a>
    </td>
    <td align="center" width="200px">
      <a href="https://github.com/stopmin" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/108014449?v=4" width="100px" alt="정지민 프로필" />
      </a>
    </td>
    <td align="center" width="200px">
      <a href="https://github.com/jjt4515" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/87135698?v=4" width="100px" alt="정진택 프로필" />
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/amm0124" target="_blank">김건호</a>
    </td>
    <td align="center">
      <a href="https://github.com/canyos" target="_blank">이풍헌</a>
    </td>
    <td align="center">
      <a href="https://github.com/stopmin" target="_blank">정지민</a>
    </td>
    <td align="center">
      <a href="https://github.com/jjt4515" target="_blank">정진택</a>
    </td>
  </tr>
</table>
</div>
<h3 align="center"> FrontEnd</h3>

<div align="center">
<table align="center">
  <tr>
    <td align="center" width="200px">
      <a href="https://github.com/jasper200207" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/51306225?v=4" width="100px" alt="김건호 프로필" />
      </a>
    </td>
    <td align="center" width="200px">
      <a href="https://github.com/rudtj" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/98938406?v=4" width="100px" alt="이풍헌 프로필" />
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/amm0124" target="_blank">김도균</a>
    </td>
    <td align="center">
      <a href="https://github.com/canyos" target="_blank">이경서</a>
    </td>
  </tr>
</table>
</div>
