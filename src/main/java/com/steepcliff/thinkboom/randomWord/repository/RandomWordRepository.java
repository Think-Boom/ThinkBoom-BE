package com.steepcliff.thinkboom.randomWord.repository;


import com.steepcliff.thinkboom.randomWord.model.RandomWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RandomWordRepository extends JpaRepository<RandomWord,Long> {
    Optional<RandomWord> findByUuId(String uuId);
}
