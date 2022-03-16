package com.steepcliff.thinkboom.randomWord.repository;


import com.steepcliff.thinkboom.randomWord.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word,Long> {
    Word findWordByWord(String word);
}
