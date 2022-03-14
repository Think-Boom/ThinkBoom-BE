package com.steepcliff.thinkboom.randomWord.repository;


import com.steepcliff.thinkboom.randomWord.model.RandomWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomWordRepository extends JpaRepository<RandomWord,Long> {
    RandomWord findByUuId(String uuId);
}
