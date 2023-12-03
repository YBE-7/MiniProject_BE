# 미니 프로젝트 : 숙박 예약 API 서비스 
## 서비스 : https://mini-team-7.vercel.app


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
