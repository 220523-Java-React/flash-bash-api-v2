package com.revature.flashbash.repository;

import com.revature.flashbash.model.Flashcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    List<Flashcard> findAllByCreator_UserId(Integer userId);
    List<Flashcard> findAllByCreator_Username(String username);
    List<Flashcard> findAllByTopic(Flashcard.Topic topic);

    Long deleteByFlashcardId(Integer flashcardId);
}
