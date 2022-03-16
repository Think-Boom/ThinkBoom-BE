package com.steepcliff.thinkboom.randomWord.repository;


import com.steepcliff.thinkboom.randomWord.model.RandomWord;
import com.steepcliff.thinkboom.randomWord.model.RwWd;
import com.steepcliff.thinkboom.randomWord.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RwWdRepository extends JpaRepository<RwWd,Long> {
    @Query("select r.word from RwWd r where r.randomWord=:randomWord")
    List<Word> findWordByRandomWord(RandomWord randomWord);
}
