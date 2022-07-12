package com.revature.flashbash.repository;

import com.revature.flashbash.model.Flashcard;
import com.revature.flashbash.model.Flashcard.Topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    List<Flashcard> findAllByCreator_UserId(Integer userId);
    List<Flashcard> findAllByCreator_Username(String username);
    List<Flashcard> findAllByTopic(Topic topic);

    Long deleteByFlashcardId(Integer flashcardId);
}
