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
    Page<Flashcard> findAllByDifficultyLessThanEqual(Flashcard.Difficulty difficulty, Pageable pageable);

    Page<Flashcard> findAllByDifficultyLessThanEqualAndTopic(Flashcard.Difficulty difficulty, Flashcard.Topic topic, Pageable pageable);

    Page<Flashcard> findAllByCreator_UserIdAndTopic(Integer userId, Flashcard.Topic topic, Pageable pageable);
    Page<Flashcard> findAllByCreator_UserIdAndDifficultyLessThanEqual(Integer userId, Flashcard.Difficulty difficulty, Pageable pageable);
    Page<Flashcard> findAllByCreator_UserIdAndDifficultyLessThanEqualAndTopic(
            Integer userId, Flashcard.Difficulty difficulty, Flashcard.Topic topic, Pageable pageable);

    Long deleteByFlashcardId(Integer flashcardId);
}
