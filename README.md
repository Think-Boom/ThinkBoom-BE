# ThinkBoom-BE
# Triptalk 🏝️
여행하면서 찍은 사진과 평가, 후기들을 공유하는 사이트입니다!
게시글은 모두가 볼 수 있고, 댓글 기능과 좋아요 기능은 로그인한 유저만 이용할 수 있습니다.
마이페이지에서 작성한 글과 좋아요를 누른 글을 볼 수 있습니다.


## 1. 제작기간 & 팀원 소개
- 2022년 2월 10일 ~ 2022년 2월 16일
- 프론트 : 이주영 소정현 [Github](https://github.com/jyi3479/mini-project-front)
- 백엔드 : 이혁준 박재균 김채경 [Github](https://github.com/KimCG1130/miniproject)

[Triptalk 보러가기](http://triptalk.com.s3-website.ap-northeast-2.amazonaws.com)
![미니프로젝트 사진](https://user-images.githubusercontent.com/94282246/154506847-ca0ca7ff-983b-4843-99e3-0350ad2ba70f.png)

## 2. API 설계
[https://www.notion.so/Trip-Talk-46f9f72bdb4441f88b1d84b28eaf0d5f](https://www.notion.so/Trip-Talk-46f9f72bdb4441f88b1d84b28eaf0d5f)


## 3. 와이어프레임

![와이어프레임1](https://user-images.githubusercontent.com/94282246/154503570-98cb1d44-f181-4159-b0a7-b7417e89c109.png)
![와이어프레임2](https://user-images.githubusercontent.com/94282246/154503578-6e11ef2c-0740-4f90-84c2-d58f2c191a02.png)
![와이어프레임3](https://user-images.githubusercontent.com/94282246/154503581-c70e9f3b-f2a7-4fb1-89d6-9d83b1dd7a18.png)


## 4. DB 설계
![DB 설계 사진](https://user-images.githubusercontent.com/94282246/154505490-fd284620-5cee-4177-95ed-aa34151c9de4.png)


## 5. Trouble Shooting
- CORS 오류 해결 : 백엔드와 프론트 중 한쪽에서 설정을 변경하여 해결(프론트 쪽에서 proxy를 사용하고 백엔드에서도 설정을 해주다가 백엔드만 설정하기로 합의)
- 로그인 방식 변경 : JSESSIONID 방식을 사용했으나, 프론트와 연결이 불가능하다는 판단 후(https 문제 등) JWT 방식으로 변경
- 로그인 유지 : 로그인 후 유저 정보를 GET 요청으로 받아와서 유저 권한을 유지하려고 했으나, 새로고침 등에 대비하여 local storage에 nickname(중복검사된 값)을 로그인 여부와 정보로 활용
- 좋아요 기능 : 좋아요 누를 때마다 좋아요 수가 갱신되도록 상태값 관리
- 이미지 미리보기 기능 및 업로드 : 미리보기를 상태값으로 관리 후 작성하기 누른 이미지만 서버로 저장
