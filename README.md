![띵킹붐로고_완_1 (1) (1)](https://user-images.githubusercontent.com/97424544/162109937-28746a8d-2845-497e-9d3e-2d32c6c8238a.png)
-------------------------
아이디어 도출을 도와주는 웹 사이트
- 브레인스토밍을 통한 아이디어 도출 회의에서 어려움을 겪는 팀 혹은 개인을 도와주기 위해 만들어진 아이디어 도출 도구입니다.
- 총 3가지의 활동을 통해 생각지도 못한 다양한 아이디어를 도출해내고 그 아이디어를 확장시켜 좋은 아이디어 도출 회의가 될 수 있도록 도와줍니다.
- 또한 회의했던 내용이 궁금하거나 다른 사람의 아이디어가 궁금하다면 다시 찾아볼 수 있도록 준비하였습니다.
<br><br>

프로젝트 기간
------
2022년 02월 25일 부터 2022년 04 13일
<br><br>
BackEnd GIT : https://github.com/Think-Boom/ThinkBoom-BE
<br>
FrontEnd GIT : https://github.com/Think-Boom/ThinkBoom-FE

팀 구성
----
|이름|포지션|
|:---:|:---:|
|한동윤|프론트엔드|
|노예찬|프론트엔드|
|이승민|백엔드|
|김채경|백엔드|
|최원준|백엔드|
|강경지|디자이너|
|김정현|디자이너|


<br><br>
## 핵심 기능


### :question: RandomWord
- **다양한 자극을 받을 수 있습니다!**


랜덤워드는 주어진 주제와 관련이 없는 단어를 랜덤(무작위)로 선정해 이를 주제와 연관시켜 강제로 아이디어를 발산하는 기법입니다.


- **다양한 단어 조합을 해볼 수 있습니다!**


주제와 관련없는 랜덤한 8개의 단어들 중에 6개를 선택해서 아이디어를 도출해볼 수 있어요.

<br>

### 🧠 BrainWriting
- **오직 아이디어에만 집중할 수 있습니다!**


아이디어 회의를 할 때 사람들은 아이디어만을 순수하게 생각하는 것이 아니라 사람들의 영향력도 같이 생각하게 된다고 합니다. 따라서 브레인 라이팅은 순수하게 아이디어에 대해서만 생각하기 위해서 만들어졌습니다.


- **실시간 채팅 기능을 사용 가능합니다!**


실시간 채팅 기능을 통해 브레인라이팅을 사용할 때 의사소통을 편하게 할 수 있습니다.


- **인터렉티브한 기능을 사용 가능합니다!**


서로 인터렉티브하게 아이디어와 아이디어에 대한 코멘트를 적고 공유할 수 있습니다. 마지막으로 투표를 통해 무슨 아이디어가 제일 적절한지도 판단할 수 있어요!


<br>

### 🎩 SixHat
- **다양한 관정으로 논의 가능합니다!**


‘여섯가지 생각모자’ 기법은 6개의 생각의 방향을 제시하여 다양한 관점으로 논제에 대해 토의 할 수 있는 토의 기법입니다.


- **다양한 역할이 준비되어 있습니다!**


다인용 기능으로 최대 8명의 인원이 모여 토의를 진행하되 각자 자신이 고른, 혹은 배당받은 모자의 시선에 충실하여 토론을 진행합니다.


- **모자를 무작위로 배정할 수 있습니다!**


토론은 실시간 채팅으로 진행되며, 주제를 선정하고 모자를 무작위로 배분할 수 있는 권한을 가진 방장과 그 외 멤버들로 구성되어 진행됩니다.  

<br>

## API 설계
### RandomWord
|기능|Method|URL|
|:---|:---:|:---:|
|무작위단어 불러오기|GET|/randomWord|
|단어리스트 저장|POST|/randomWord|
|RandomWord 공개여부설정|PATCH|/randomWord/share/{rwId}|
<br>

### BrainWriting
|기능|Method|URL|
|:---|:---:|:---:|
|소켓연결|GET|/websocket|
|BrainWriting 방생성|POST|/api/brainwriting/rooms|
|BrainWriting 닉네임 입력|POST|/api/brainwriting/user/nickname|
|BrainWriting 채팅방 구독|socket|/sub/api/brainwriting/rooms/{bwRoomid}|
|BrainWriting 채팅 입력|socket|/pub/api/brainwriting/chat/message|
|BrainWriting 카드 생성(준비)|POST|/api/brainwriting/idea/{bwroomid}|
|BrainWriting 아이디어 입력|PATCH|/api/brainwriting/idea/{bwRoomid}|
|BrainWriting 아이디어 받기|GET|/api/brainwriting/idea/{bwroomid}?userId=Long|
|BrainWriting 코멘트입력|POST|/api/brainwriting/comment/{bwroomid}|
|BrainWriting 투표 뷰|GET|/api/brainwriting/voteview/{bwroomid}|
|BrainWriting 튜표하기|PATCH|/api/brainwriting/vote/{bwroomid}|
|BrainWriting 공유여부|PATCH|/api/brainwiting/sharing/{bwroomid}|
|BrainWriting 아이디어 및 코멘트 작성 시간 갱신하기|PATCH|/api/brainwriting/timer/{bwroomid}|
|BrainWriting 투표 시간 갱신하기|PATCH|/api/brainwriting/vote/timer/{bwroomid}|
|BrainWriting 남은 시간 전달|GET|/api/brainwriting/timer/{bwroomid}|
|BrainWriting Gallery에 저장|POST|/api/brainwriting/gallery/save/{bwroomid}|
<br>

### SixHat
|기능|Method|URL|
|:---|:---:|:---:|
|SixHat 방생성|POST|/api/sixHat/rooms|
|SixHat 닉네임 입력|POST|/api/sixHat/user/nickname|
|SixHat 채팅방 구독|socket|/subSH/api/sixHat/rooms/{shroomId}|
|SixHat 채팅 입력|socket|/pubSH/api/sixHat/chat/message|
|SixHat 공유 여부|POST|/api/sixHat/sharing/{shroomid}|
|SixHat 남은 시간 주기|GET|/api/sixHat/timer/{shroomid}|
|SixHat 시간 갱신하기|POST|/api/siHat/timer/{shRoomId}|
|Gallery에 저장|POST|/api/sixHat/save/gallery/{shRoomId}|
|SixHat 결과|GET|/api/gallery/sixhat/{shroomid}|
<br>

### Gallery
|기능|Method|URL|
|:---|:---:|:---:|
|갤러리 메인페이지 데이터|GET|/api/gallery?page={page}&size={갯수}|
|랜덤워드 갤러리 상세 데이터|GET|/api/gallery/randomword/{rwid}|
|브레인 라이팅 갤러리 상세 데이터|GET|/api/gallery/brainwriting/{bwroomid}|
|식스햇 갤러리 상세 데이터|GET|/api/gallery/sixhat/{shroomid}|

<br><br>
## ERD

<br><br>
## 시연 영상
https://youtu.be/Mg18Rx5wkS8  

<br><br>
## 서비스 아키텍쳐
![서비스 아키텍쳐](https://user-images.githubusercontent.com/97424544/162121668-3c45183f-4146-444c-8962-ccfc9f8fa666.png)


