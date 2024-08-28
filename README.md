 
# 한 발자국도 걸을 수 없다
[![한발자국도걸을수없다](https://github.com/user-attachments/assets/e954930e-58cf-4382-884e-263f4b15cdea)](https://youtu.be/AVsQdcvikPM)
- 이미지 클릭 시, 이번역 소개 영상 페이지로 이동합니다.(youtube)


### < 기획 의도 >
* 한 발자국도 걸을 수 없다.
  * 현대인에게 지하철은 가장 중요한 교통 수단의 하나
  * 환자, 장애인을 비롯한 약자와 짐이 많은 관광객들의 지하철 이용 시 편의성 증가
  * 전국 모든 지하철 역의 **10가지 정보 제공**
  * 역 까지의 **길 찾기** 기능 및 **역 정차 시 해당 역 조회** 기능, **호선 별 채팅** 서비스 등으로 지하철과 관련된 이야기를 나눌 수 있는 공간 제공
 
 ### < 서비스 소개 >
* 회원 가입 및 로그인 기능
* 지하철 역 내 편의 시설 조회 기능
* 도보, 자전거를 활용한 길 찾기 기능
* 지하철 호선, 지역별 실시간 채팅 기능

### < 팀원 구성 및 프로젝트 기간 >
* 기간 : 2024.07.02 ~ 2024.08.28
* 팀원 구성
  
  |정은아|임혜연|김보민|권윤슬|
  |:---:|:---:|:---:|:---:|
  |팀장, BE|BE|FE|FE|

### < 기술 스택 >
#### ✔️Frond-end
        * Library : React
        * Styling : styled-components
        * Data Fetching : axios
        * State Management : redux js toolkit
        * State Form : React Hook Form
        * Module Bundler : Vite

#### ✔️Back-end
        * Framework : Spring
        * Library : SpringBoot, Lombok, Spring Web, Validation, Spring Security, jwt, Oauth2.0, jbcrypt, AWS
        * Databse : Oracle
        * Language : Java 17 

### < 아키텍쳐 >
<img src="https://github.com/None-Step/None-Step-BE/blob/main/Architecture.png" width="600" height="400"/>

### < 메인 기능 소개> 

**1. 회원 가입 및 로그인**

![member](https://github.com/user-attachments/assets/89776a24-4c20-4573-a373-a1bfac49c7bc)

   * 카카오, 네이버 소셜 로그인
   * 일반 로그인
   * JWT 기반 Refresh Token, Access Token 사용

**2. 지하철 탑승 후 정차시 역 조회**

![bong](https://github.com/user-attachments/assets/f0e574cd-421c-4212-b6e6-95776fb18a6e)
![guro](https://github.com/user-attachments/assets/dedd063a-1c6d-42f8-b170-31b0483c1b0b)

   * 구형 좌표계를 이용하여 사용자 위치에서 가장 가까운 역 1개 추출
   * 프론트에서 A방식 → B방식으로 변경하여 IPhone에서도 역 조회 가능
   * 추적 시작 후 중지하지 않고 지속적으로 역 조회 가능
   * 추적 시작 → 추적 중지 버튼 반복적으로 누르며 역 조회 가능
  
**3. 지도(편의시설 바로가기)**

![subsub](https://github.com/user-attachments/assets/46ab2bdd-a76c-4725-839f-542835c084f8)

- 구형좌표계를 이용하여 지도의 일정 반경 내 위치한 역 조회 가능
    - 지도에서 마커 선택 시, 마커 별 정보 조회 가능
    - 조회 가능한 편의 시설
       - 데이터의 경우, 공공데이터 포털(https://www.data.go.kr/) 및 철도 데이터 포털(https://data.kric.go.kr/rips/)에서 제공하는 공공 데이터를 가공하여 사용하였음<br>
          - 역 정보 전체
          - 엘리베이터 위치
          - 출구별 에스컬레이터 위치
          - 화장실
          - 장애인 화장실
          - 휠체어 리프트 위치
          - 수유실
          - ATM
          - 제세동기
          - 전동 휠체어 충전 설비
          - 고객 센터
        
**4. 길 찾기**

![roadroad](https://github.com/user-attachments/assets/eb7696cf-80d8-414f-8fb4-d686a6b9726a)

- 출발지, 도착지 입력 시 최단 거리 + 계단 없는 경로로 SK API를 활용하여 도보 길 안내
    - 목적지가 역일 경우 가까운 엘리베이터, 에스컬레이터 출구로 안내
    - 실시간 공공 자전거 대여 API 서울, 대전 지역 안내
    - 현재 위치 조회 가능
    - 빠른 경로 탭(명칭 변경 예정)을 통해 주변에 가장 가까운 역 안내
    - 출발 위치와 도착 위치를 작성하면 빠른 지하철 역으로 안내


**4-1. 지하철 경로 안내**

![subwayPath](https://github.com/user-attachments/assets/5d3acd08-8a4a-4540-a4c5-dcbec2c6dd2a)

- 출발지와 도착지에 역이 존재할 경우 가까운 지하철 역의 경로를 함께 제공
- 최단거리 경로로 안내
  
**5. 채팅(WebSocket STOMP)**

![chatchat](https://github.com/user-attachments/assets/bffd2948-3e45-40ba-abb2-e31bb8e607bc)

   - 메인 화면에서 해당 지역을 클릭하여 실시간 채팅 참여 가능
     - 지역 별 전체 탭에서는 지역 내 모든 호선의 실시간 채팅 조회 가능
     - 각 호선 별로 탭이 제공되어 원하는 호선에서 실시간 채팅 가능
     - 송신한 메시지 실시간 삭제 가능
     - 채팅 메시지 복사 가능
   - 메뉴바 채팅에서 원하는 지역을 클릭하여 채팅 참여 가능
     - 원하는 지역을 클릭하면 전체 혹은 각 호선 선택하여 대화 참여 가능

### <ERD 다이어그램>
<img src="https://github.com/None-Step/None-Step-BE/blob/main/ERD.png" />

