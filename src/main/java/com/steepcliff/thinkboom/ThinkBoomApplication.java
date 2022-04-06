package com.steepcliff.thinkboom;

import com.steepcliff.thinkboom.randomWord.model.Word;
import com.steepcliff.thinkboom.randomWord.repository.WordRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Set;

@EnableJpaAuditing
@SpringBootApplication
public class ThinkBoomApplication {

    public static void main(String[] args) {

        SpringApplication.run(ThinkBoomApplication.class, args);
    }
// 크롤링해서 DB에 단어를 추가하는 코드
//    @Bean
//    public CommandLineRunner save(WordRepository wordRepository) {
//        return (args) -> {
//            String Url = "https://ko.wiktionary.org/wiki/%EB%B6%80%EB%A1%9D:%EC%9E%90%EC%A3%BC_%EC%93%B0%EC%9D%B4%EB%8A%94_%ED%95%9C%EA%B5%AD%EC%96%B4_%EB%82%B1%EB%A7%90_5800";
//            Document doc = Jsoup.connect(Url).get();
//            Elements elem = doc.select("table.prettytable");
//            Set<String> wordSet=new HashSet<>();
//            for (Element e : elem.select("a")) {
//                wordSet.add(e.text());
//            }
//            for(String w:wordSet){
//                Word word=new Word();
//                word.setWord(w);
//                wordRepository.save(word);
//            }
//
//        };
//    }
}
