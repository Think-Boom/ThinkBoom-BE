package com.steepcliff.thinkboom.randomWord.service;


import com.steepcliff.thinkboom.gallery.Gallery;
import com.steepcliff.thinkboom.gallery.GallerySaveResponseDto;
import com.steepcliff.thinkboom.gallery.GalleryService;
import com.steepcliff.thinkboom.randomWord.dto.RwRequestDto;
import com.steepcliff.thinkboom.randomWord.dto.RwResponseDto;
import com.steepcliff.thinkboom.randomWord.model.RandomWord;
import com.steepcliff.thinkboom.randomWord.model.RwWd;
import com.steepcliff.thinkboom.randomWord.model.Word;
import com.steepcliff.thinkboom.randomWord.repository.RandomWordRepository;
import com.steepcliff.thinkboom.randomWord.repository.RwWdRepository;
import com.steepcliff.thinkboom.randomWord.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RandomWordService {
    private final RandomWordRepository randomWordRepository;
    private final WordRepository wordRepository;
    private final RwWdRepository rwWdRepository;
    private final GalleryService galleryService;

    //DB에서 랜덤한 단어 받아오기
    public List<String> getWord(){
        //난수를 발생하기 위한 random 객채 생성
        Random random=new Random();
        //리턴할 wordDtoList 객체 생성
        List<String> wordList = new ArrayList<>();
        //사용자에게 전달할 단어의 갯수가 6개 이므로 리스트의 크기가 6이 될때까지 반복
        while (wordList.size()!=6){
            //random.nextInt를 통해 렌덤한 숫자 반환 (0이 나올수 있기때문에 +1)
            int rint = random.nextInt(5467)+1;
            //DB에서 rint와 id가 같은 단어를 찾아옴
            Word word=wordRepository.findById(Long.valueOf(rint)).orElseThrow(
                    ()->new NullPointerException("찾을단어가 없습니다.")
            );
            String wordStr = word.getWord();

            wordList.add(wordStr);
        }
        return wordList;
    }

    //사용자가 전달한 단어들을 DB에 저장하기
    public RwResponseDto saveWord(RwRequestDto requestDto){
        //사용자에게 전달받은 단어 리스트
        List<String> wordDtoList = requestDto.getWordList();
        RandomWord randomWord = new RandomWord();
        //UUID를 통해 고유값 전달(중복될 가능성이 있어서 추후 수정할 수도 있음)/UUID가 너무 길기 때문에 8글자로 자름
        String uuid = UUID.randomUUID().toString().substring(0,8);
        randomWord.setUuId(uuid);
        randomWord.setSubject(requestDto.getSubject());
        randomWordRepository.save(randomWord);
        log.info("여기까지 진행1");
        //전달받은 단어 리스트를 하나씩 꺼내서 DB에 저장
        for(String w : wordDtoList){
            Word word = wordRepository.findWordByWord(w);
            RwWd rwWd = new RwWd();
            rwWd.setWord(word);
            rwWd.setRandomWord(randomWord);
            rwWdRepository.save(rwWd);
        }
        RwResponseDto rwResponseDto = new RwResponseDto();
        rwResponseDto.setWordList(wordDtoList);

        rwResponseDto.setRwId(uuid);
        log.info("여기까지 진행2");
        // 갤러리 db에 저장
        GallerySaveResponseDto gallerySaveResponseDto = new GallerySaveResponseDto();
        gallerySaveResponseDto.setRoomId(uuid);
        gallerySaveResponseDto.setType(Gallery.RoomType.RW);
        gallerySaveResponseDto.setTitle("랜덤워드");
        gallerySaveResponseDto.setSubject(requestDto.getSubject());
        galleryService.saveGallery(gallerySaveResponseDto);

        return rwResponseDto;
    }

    //공유 여부 변경
    @Transactional
    public String shareCheck(String uuId) {
        //전달받은 UUID로 randomword객체를 찾아서 공유여부 변경
        RandomWord randomWord = randomWordRepository.findByUuId(uuId).orElseThrow(
                ()->new NullPointerException("수정할 랜덤워드 결과가 없습니다.")
        );

        randomWord.update();
        galleryService.deleteGallery(uuId);
        return "success";
    }

    //랜덤워드 결과물에 대한 상세 정보 반환
    public RwResponseDto getRwGallery(String uuId) {
        RandomWord randomWord = randomWordRepository.findByUuId(uuId).orElseThrow(
                ()->new NullPointerException("반환할 랜덤워드 결과가 없습니다.")
        );
        List<String> wordDtoList=new ArrayList<>();
        List<Word> dbWordList=rwWdRepository.findWordByRandomWord(randomWord);
        for(Word w : dbWordList){
            wordDtoList.add(w.getWord());
        }
        RwResponseDto rwResponseDto=new RwResponseDto();
        rwResponseDto.setRwId(uuId);
        rwResponseDto.setWordList(wordDtoList);
        return rwResponseDto;
    }

    //api를통해 단어와 뜻을 받아오는 코드
//    private static final String apiURL = "http://opendict.korean.go.kr/api/view?" +
//            "key=6F09E1C7F1254505269E716416BD974E&" +
//            "method=target_code&" +
//            "req_type=json&" +
//            "q=";
//    Random random = new Random();
//
//    public List<WordDto> getWord() {
//        List<WordDto> responseWordDtoList=new ArrayList<>();
//        while (responseWordDtoList.size() != 8) {
//            try {
//                int ran = random.nextInt(1000000);
//                System.out.println(ran);
//                URL url = new URL(apiURL + ran);
//                BufferedReader bf =
//                        new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
//                String result;
//                StringBuffer resultBuffer = new StringBuffer();
//                while ((result = bf.readLine()) != null) {
//                    resultBuffer.append(result);
//                }
//                String js = String.valueOf(resultBuffer);
//                JSONParser jsonParser = new JSONParser();
//                JSONObject jsonObject = (JSONObject) jsonParser.parse(js);
//
//                JSONObject channelJson = (JSONObject) jsonObject.get("channel");
//                JSONObject itemJson = (JSONObject) channelJson.get("item");
//
//                JSONObject wordInfoJson = (JSONObject) itemJson.get("wordInfo");
//                String word = String.valueOf(wordInfoJson.get("word"));
//                word = word.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");
//
//                JSONObject senseInfoJson = (JSONObject) itemJson.get("senseInfo");
//                String desc = String.valueOf(senseInfoJson.get("definition"));
//                desc = desc.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");
//
//
//                WordDto responseWordDto=new WordDto();
//                responseWordDto.setWord(word);
//                responseWordDto.setContents(desc);
//                responseWordDtoList.add(responseWordDto);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return responseWordDtoList;
//    }

//    public RwResponseDto saveWord(RwRequestDto requestDto) {
//        List<WordDto> wordDtoList=requestDto.getWordList();
//        RandomWord randomWord = new RandomWord();
//        RandomWord rw = randomWordRepository.save(randomWord);
//
//        List<String> dbWordList = wordRepository.findWord();
//        for (int i = 0; i < wordDtoList.size(); i++) {
//            WordDto wordDto=wordDtoList.get(i);
//            if (!dbWordList.contains(wordDto.getWord())) {
//                Word word = new Word();
//                word.setWord(wordDto.getWord());
//                wordRepository.save(word);
//
//                RwWd rwWd = new RwWd();
//                rwWd.setRandomWord(rw);
//                rwWd.setWord(word);
//                rwWdRepository.save(rwWd);
//            }
//        }
//
//        RwResponseDto rwResponseDto = new RwResponseDto();
//        rwResponseDto.setRwId(rw.getRwId());
//        rwResponseDto.setWordList(requestDto.getWordList());
//        return rwResponseDto;
//    }
//
//    public String shareCheck(Long rwId) {
//        RandomWord randomWord = randomWordRepository.findById(rwId).orElseThrow(
//                () -> new NullPointerException("공유여부를 수정할 글이 없습니다.")
//        );
//        randomWord.update();
//        return "success";
//    }
//
//    public RwResponseDto getRwGallery(Long rwId) {
//        RwResponseDto rwResponseDto=new RwResponseDto();
//        List<WordDto> wordDtoList=new ArrayList<>();
//        RandomWord randomWord=randomWordRepository.findById(rwId).orElseThrow(
//                ()->new NullPointerException("랜덤워드 결과가 없습니다.")
//        );
//        rwResponseDto.setRwId(randomWord.getRwId());
//
//        List<Word> wordList=rwWdRepository.findWordByRandomWord(randomWord);
//        for(Word w:wordList){
//            WordDto wordDto=new WordDto();
//            wordDto.setWord(w.getWord());
//            wordDtoList.add(wordDto);
//        }
//        rwResponseDto.setWordList(wordDtoList);
//        return rwResponseDto;
//    }
}

