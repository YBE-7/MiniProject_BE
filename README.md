# 미니 프로젝트 : 숙박 예약 중계 서비스 
## 서비스 : https://mini-team-7.vercel.app (폐쇄)


## 🖥 프로젝트 개요
> 1. 프로젝트 주제 :  숙박 예약 웹서비스 구현 프로젝트
> 2. 프로젝트 기간 : 11월 20일(월) ~ 12월 01일(금)
> 3. 주요 목표 : Spring Boot, DB 설계, DB 트랜잭션, RESTful API 설계, 테스트 코드 작성, Spring Security 적용

## ⚙️ Project Settings
> 1. Java 버전 : 17
> 2. 빌드 & 빌드 도구 : Gradle
> 3. Git 브랜치 전략 : Feature Branch → Develop Branch → Main Branch

## 🛠️ 기술 스택
#### Framework
![Spring](https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![springboot](https://img.shields.io/badge/springboot-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![springsecurity](https://img.shields.io/badge/springsecurity-6DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)

#### DB
![MongoDB](https://img.shields.io/badge/H2-%234ea94b.svg?style=for-the-badge&logo=htop&logoColor=white)
![mysql](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

#### Infra
<img src="https://img.shields.io/badge/AWS EC2-E34F26?style=for-the-badge&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/AWS LB-E34F26?style=for-the-badge&logo=awsorganizations&logoColor=white">
<img src="https://img.shields.io/badge/AWS RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/AWS Rout53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white">

## ⭐ JIRA 링크
[JIRA](https://ybeminiproject.atlassian.net/jira/core/projects/FIRST/list?sortBy=labels&direction=DESC)

## ERD 설계
<img width="1442" alt="image" src="https://github.com/YBE-7/YBE-Mini-Project-BE/assets/114489245/51c4d2e1-c4f4-445b-95a0-70f63e133c79">

## 📌 개발 기능 및 API 설계 
API 설계서 Link : [API 명세서](https://github.com/Hwang-Kyu-Cheol/yanolja_mini_wiki/wiki)

### 1. 로그인/회원가입 기능
> 1. 기본 정보는 ID 역할로 이메일 주소와, 비밀번호, 이름 입니다.
> 2. 이메일과 비밀번호로 로그인할 수 있습니다.
> 3. 회원 정보를 저장해둔 데이터베이스를 검색하여 해당 사용자가 유효한 사용자 인지 판단합니다.
> 4. 상품 조회(전체, 개별), 회원 가입은 로그인 없이 사용 가능합니다.
> 5. 이 외 기능은 로그인이 필요합니다.

### 2. 전체 상품 조회 기능
> 1. 데이터베이스에서 전체 상품 목록을 가져옵니다.
> 2. 재고에 따라 품절일 경우, 품절로 표시합니다.
> 3. 카테고리를 분류하여, 상품을 출력합니다.

### 3. 개별 상품 조회 기능
> 1. 전체 상품 목록에서 특정 상품 이미지를 클릭하면 해당 상품에 대한 상세 정보를 상품에 저장해 둔 데이터베이스에서 가져옵니다.
> 2. 특정 숙박업소 하위에 예약 가능한 객실 리스트를 제공합니다.

### 4. 장바구니 담기 기능
> 1. 장바구니에 담긴 상품 데이터에 대한 상품별 구매 금액, 전체주문 합계 금액 등을 계산합니다.
> 2. 체크 박스를 통해 결제할 상품을 선택/제외할 수도 있습니다.
> 3. 주문하기 버튼을 통해 주문/결제 화면으로 이동합니다.

### 5. 주문하기 기능
> 1. 사용자가 주문하기를 클릭하면 사용자 계정에 따른 주문 정보를 저장합니다.
> 2. 주문 리스트를 마이페이지에서 확인할 수 있습니다.
> 3. 사용자가 정상적으로 상품을 주문하면 해당 상품의 주문 가능 개수가 줄어듭니다.

## 🧑🏻‍💻 팀 소개 및 역할
### ✨ BE 팀 소개
| 서원빈 (BE) <br> | 정혜민 (BE) <br> | 황규철 (BE) <br> | 홍용현 (BE) <br> |
|:------------------:|:------------------:|:------------------:|:------------------:|
| ![서원빈](https://avatars.githubusercontent.com/u/81563920?v=4&size=300) | ![정혜민](https://avatars.githubusercontent.com/u/114489245?v=4&size=300) | ![황규철](https://avatars.githubusercontent.com/u/67046364?v=4&size=300) | ![홍용현](https://avatars.githubusercontent.com/u/108213846?v=4&size=300)  |
| [WonBin](https://github.com/Wonbn)| [HyeMin](https://github.com/HyemIin) | [KyuCheol](https://github.com/Hwang-Kyu-Cheol) | [YoungHyen](https://github.com/dydgus1052) |
| <ul><li>장바구니 API</li><li>AWS LB</li> | <ul><li>회원 API</li></ul><ul><li>AWS EC2,RDS</li></ul> | <ul><li>숙소 API</li><li>객실타입 API</li> | <ul><li>주문 API</li> |

## 서버 실행 화면
<img width="1694" alt="image" src="https://github.com/YBE-7/YBE-Mini-Project-BE/assets/114489245/4cd42166-e1b8-4c2e-9464-8da5ae833754">

---

## KPT 보완 사항
### 1. Lock에 Redis 적용
> 1. 기존 MySQL의 namedlock을 이용해서 주문의 동시성 처리를 했던 반면, Redis의 redisson을 이용해서 동시성 처리하는 것으로 바꾸었습니다.
> 2. 이유는 서버가 Scale-out 되었을때도 동시성 처리를 할 수 있고, redisson을 이용하면 쉽게 락을 구현할 수 있기 때문입니다.
> 3. 이 후 테스팅을 진행해본 결과, 제대로 동시성 처리가 됨을 확인할 수 있었습니다.

### 2. 아이디 찾기 기능 추가
> 1. 사용자가 회원가입 시 등록한 휴대폰 번호를 기반으로 ID(email)을 찾을 수 있는 기능을 추가했습니다.
> 2. 휴대폰 번호는 유일 값으로 DB에 등록하여 중복을 방지했습니다.

## 회고
### 황규철
##### 좋았던 점
> 1. QueryDSL을 깊이있게 사용해볼 수 있어서, 이해도가 한층 높아졌다.
> 2. 동시성 처리의 개념을 알아볼 수 있었고, 직접 구현해보며 커머스 서비스의 동시성 처리 구조를 엿볼 수 있었다.
> 3. 프론트와 통신하면서 https를 http로 바꿔야할 때가 있었는데, 이 때 로드밸런서를 이용해서 하며 네트워크에 대해 이해도를 높일 수 있었다.
> 4. 좋은 팀원들을 만났다😄

##### 아쉬웠던 점
> 1. 일정이 촉박하다 보니 코드리뷰를 서로 자세하게 진행하지 못했다. 다음에는 꼼꼼하게 해보고 싶다.
> 2. 테스트 코드를 세밀하게 작성하지 못했다. 다음에는 테스트 코드 또한 꼼꼼하게 작성해야겠다.

### 정혜민
##### 좋았던 점
> 1. AWS 서버를 통해 배포까지 해보는 경험을 할 수 있었기에, 스스로 온전히 배포를 할 수 있는 실력을 얻었다.
> 2. https를 위해 AWS 로드 밸런서를 사용해본 경험이 새로웠다.
> 3. api 공통 포맷을 활용하여 설계한 경험을 통해 설계 기반을 쌓을 수 있었다.
> 4. 모두 각자의 역할을 잘 수행해줬기 때문에 좋은 기억만 얻은 채 프로젝트를 마무리할 수 있었다!

##### 아쉬웠던 점
> 1. 기능 구현하면서 배우는 부분도 많았을 것 같은데 시큐리티 설정 및 서버 인프라 쪽에 집중하다보니 기능 구현 부분에 기여하지 못한 것이 아쉽다.
> 2. 도커를 활용해 배포해보지 못한 부분이 아쉬웠다.

### 서원빈
##### 좋았던 점
> 1. 야놀자와 같은 숙박 중계 플랫폼에서 실제 겪을 수 있는 문제점과 고민을 팀원들과 공유해보고 해결해보는 좋은 경험이었다.
> 2. 단순한 CRUD API가 아닌 주문을 하기 전에 데이터를 잠시 담을 수 있는 장바구니 API를 개발하는 새로운 경험을 할 수 있었다.
> 3. 단위 테스트를 하면서 Mock 객체를 이용하여 각 Layer마다 독립적으로 테스트하는 방법을 알 수 있었다.
> 4. HTTPS 설정과 AWS 로드 밸런서를 이용한 HTTP에서 HTTPS 리디렉션 경험을 통해서 인프라 구축에 대한 경험을 할 수 있었다.
> 5. Redis를 사용하여 동시성 문제를 해결함으로써 Redisson을 이용한 분산 Lock에 대한 경험을 할 수 있었다.

##### 아쉬웠던 점
> 1. Open API를 이용해서 많은 데이터를 가져오고 등록할 때, 모두 수동으로 데이터를 가공하여 사용한 것이 아쉬웠다.
> 2. Service Layer의 단위테스트를 통합테스트 방식으로 진행한 부분이 아쉬웠다.

### 홍용현
##### 좋았던 점
> 1. 주문 API를 구현하면서 여러 테이블의 복잡한 연관관계를 파악하는 능력을 기를 수 있었다.
> 2. Mock 객체를 이용하여 각 Layer마다 독립적으로 테스트하는 방법을 알 수 있었다.
> 3. 팀원 간의 역할 분배가 잘 되었다.

##### 아쉬웠던 점
> 1. 배포 쪽으로 참여를 못했다.
> 2. 테스트 코드를 좀 더 꼼꼼하게 작성하지 못했다.
