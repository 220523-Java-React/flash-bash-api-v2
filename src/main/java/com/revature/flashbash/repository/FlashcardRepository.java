package com.revature.flashbash.repository;

import com.revature.flashbash.model.Flashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    Page<Flashcard> findAllByCreator_UserId(Integer userId, Pageable pageable);
    Page<Flashcard> findAllByCreator_Username(String username, Pageable pageable);
    Page<Flashcard> findAllByTopic(Flashcard.Topic topic, Pageable pageable);
    Page<Flashcard> findAllByDifficultyBetween(Flashcard.Difficulty low, Flashcard.Difficulty high, Pageable pageable);
    Page<Flashcard> findAllByDifficultyBetweenAndTopic(Flashcard.Difficulty low, Flashcard.Difficulty high, Flashcard.Topic topic, Pageable pageable);

    Page<Flashcard> findAllByCreator_UserIdAndTopic(Integer userId, Flashcard.Topic topic, Pageable pageable);
    Page<Flashcard> findAllByCreator_UserIdAndDifficultyBetween(Integer userId, Flashcard.Difficulty low, Flashcard.Difficulty high, Pageable pageable);
    Page<Flashcard> findAllByCreator_UserIdAndDifficultyBetweenAndTopic(
            Integer userId, Flashcard.Difficulty low, Flashcard.Difficulty high, Flashcard.Topic topic, Pageable pageable);

    Long deleteByFlashcardId(Integer flashcardId);
}
