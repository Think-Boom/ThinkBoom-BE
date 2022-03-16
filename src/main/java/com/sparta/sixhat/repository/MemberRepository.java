package com.sparta.sixhat.repository;

import com.sparta.sixhat.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
   List<Member> findAll();
   Optional<Member> findById(Long id);
   Optional<Member> findByNickname(String nickname);

}
