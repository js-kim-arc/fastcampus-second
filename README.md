# fastcampus-second->  

현재까지 Article 관련 진행 중 - 차기 게시판에서 추가 성능 중
<div align="center">
  <br />
  <img src="https://github.com/user-attachments/assets/8b291ac8-545d-42f6-92c8-83e63fc98014" alt="Cookie" width="200px" height="200px" />
  <br />
  <h1>쿠키(Cookie)</h1>
  <img src="https://github.com/user-attachments/assets/bb5b19f4-5086-4895-bfa8-67ddff7e48ca" width="600"/>
</div>

## 목차

1. [**웹 서비스 소개**](#1)
1. [**버전 기록**](#2)
1. [**기술 스택**](#3)
1. [**주요 기능**](#4)
1. [**프로젝트 구성도**](#5)
1. [**구글 애널리틱스 통계**](#6)
1. [**개발 팀 소개**](#7)
1. [**개발 기간 및 일정**](#8)
1. [**실행 방법**](#9)

<br />



<div id="9"></div>

## 💻 실행 방법

  1. AWS EC2 인스턴스 프로비저닝 및 초기 설정
      - 프론트엔드 및 백엔드 서버를 호스팅하기 위해 Amazon Web Services(AWS)에서 Ubuntu 기반 EC2 인스턴스를 생성
  
  2. Nginx와 Git 설치
      - HTTP 및 Reverse Proxy 서버 역할을 수행하기 위해 Nginx 설치
      - 배포용 애플리케이션 코드를 관리하기 위해 Git 설치
  
  3. React 기반 프론트엔드 배포
      - GitHub Repository에서 클론한 소스 코드를 빌드하여 생성된 정적 파일을 Nginx가 제공하는 기본 디렉터리로 복사
  
  4. Spring Boot 기반 백엔드 배포
      - 동일한 EC2 인스턴스에 Spring Boot 애플리케이션 Repository를 클론 후 빌드하여 실행
      - application.properties를 통해 AWS RDS(MySQL)와 MongoDB와의 연결 정보 설정
  
  5. AWS RDS 데이터베이스 설정
      - RDS 인스턴스(MySQL) 생성, 인바운드 규칙에서 EC2 인스턴스의 IP를 허용하여 네트워크 접근성 보장
      - 데이터베이스 스키마 설정, 애플리케이션이 동작하는 데 필요한 데이터 마이그레이션
  
  
  9. 테스트 및 최종 배포
      - 배포된 프론트엔드와 백엔드의 HTTP/HTTPS 요청이 정상적으로 처리되는지 테스트
      - HTTPS를 통한 보안 트래픽 처리와 도메인 연결을 성공적으로 설정하여 클라이언트와 서버 간 원활한 통신을 보장
