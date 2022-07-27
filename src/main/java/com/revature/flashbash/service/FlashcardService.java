package com.revature.flashbash.service;

import com.revature.flashbash.exception.ResourceAlreadyExistsException;
import com.revature.flashbash.exception.ResourceNotFoundException;
import com.revature.flashbash.model.Flashcard;
import com.revature.flashbash.model.User;
import com.revature.flashbash.repository.FlashcardRepository;
import com.revature.flashbash.util.PaginationOptions;
import com.revature.flashbash.util.SearchCriteria;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Service
public class FlashcardService extends PaginationService<Flashcard.SortBy>{

    @PersistenceContext
    private EntityManager entityManager;

    private final FlashcardRepository flashcardRepository;

    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public Page<Flashcard> getAllFlashcards(PaginationOptions paginationOptions, SearchCriteria searchCriteria){
        PageRequest pageRequest = buildPageRequest(paginationOptions);

        CriteriaQuery<Flashcard> cr = entityManager.unwrap(Session.class)
                .getCriteriaBuilder().createQuery(Flashcard.class);
        Root<Flashcard> root = cr.from(Flashcard.class);
        cr.select(root);



        int creator = searchCriteria.get(User.class) != null ? 1 : 0;
        int topic = searchCriteria.get(Flashcard.Topic.class) != null  ? 1 : 0;
        int difficulty = searchCriteria.get(Flashcard.Difficulty.class) != null  ? 1 : 0;

        String optionCode = String.format("%d%d%d", creator, topic, difficulty);

        switch (optionCode){
            case "000":
                return flashcardRepository.findAll(pageRequest);
            case "001":
                return flashcardRepository.findAllByDifficultyBetween(
                        ((Flashcard.Difficulty[]) searchCriteria.get(Flashcard.Difficulty.class))[0],
                        ((Flashcard.Difficulty[]) searchCriteria.get(Flashcard.Difficulty.class))[1],
                        pageRequest
                );
            case "010":
                return flashcardRepository.findAllByTopic(
                        (Flashcard.Topic) searchCriteria.get(Flashcard.Topic.class), pageRequest);
            case "011":

                return flashcardRepository.findAllByDifficultyBetweenAndTopic(
                        ((Flashcard.Difficulty[]) searchCriteria.get(Flashcard.Difficulty.class))[0],
                        ((Flashcard.Difficulty[]) searchCriteria.get(Flashcard.Difficulty.class))[1],
                        (Flashcard.Topic) searchCriteria.get(Flashcard.Topic.class),
                        pageRequest);
            case "100":
                return flashcardRepository.findAllByCreator_UserId(
                        (Integer) searchCriteria.get(User.class), pageRequest);
            case "101":
                return flashcardRepository.findAllByCreator_UserIdAndDifficultyBetween(
                        (Integer) searchCriteria.get(User.class),
                        ((Flashcard.Difficulty[]) searchCriteria.get(Flashcard.Difficulty.class))[0],
                        ((Flashcard.Difficulty[]) searchCriteria.get(Flashcard.Difficulty.class))[1],
                        pageRequest);
            case "110":
                return flashcardRepository.findAllByCreator_UserIdAndTopic(
                        (Integer) searchCriteria.get(User.class),
                        (Flashcard.Topic) searchCriteria.get(Flashcard.Topic.class),
                        pageRequest);
            case "111":
                return flashcardRepository.findAllByCreator_UserIdAndDifficultyBetweenAndTopic(
                        (Integer) searchCriteria.get(User.class),
                        ((Flashcard.Difficulty[]) searchCriteria.get(Flashcard.Difficulty.class))[0],
                        ((Flashcard.Difficulty[]) searchCriteria.get(Flashcard.Difficulty.class))[1],
                        (Flashcard.Topic) searchCriteria.get(Flashcard.Topic.class),
                        pageRequest);
            default:
                throw new IllegalStateException("Unexpected value: " + optionCode);
        }
    }

    public Flashcard getFlashcardById(Integer flashcardId){
        return flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new ResourceNotFoundException(Flashcard.class, "flashcardId", flashcardId));
    }

    public Flashcard createFlashcard(Flashcard flashcard){
        if(flashcard.getFlashcardId() != null && flashcardRepository.existsById(flashcard.getFlashcardId()))
            throw new ResourceAlreadyExistsException(Flashcard.class, "flashcardId", flashcard.getFlashcardId());

        return flashcardRepository.save(flashcard);
    }

    public Flashcard replaceFlashcard(Flashcard flashcard, Integer flashcardId){
        if(!flashcardRepository.existsById(flashcardId))
            throw new ResourceNotFoundException(Flashcard.class,"flashcardId", flashcardId);

        flashcard.setFlashcardId(flashcardId);
        return flashcardRepository.save(flashcard);
    }

    public Flashcard updateFlashcard(Flashcard flashcard, Integer flashcardId){
        Flashcard dbFlashcard = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new ResourceNotFoundException(Flashcard.class, "flashcardId", flashcardId));

        if(flashcard.getQuestion() != null)
            dbFlashcard.setQuestion(flashcard.getQuestion());

        if(flashcard.getAnswer() != null)
            dbFlashcard.setAnswer(flashcard.getAnswer());

        if(flashcard.getTopic() != null)
            dbFlashcard.setTopic(flashcard.getTopic());

        if(flashcard.getDifficulty() != null)
            dbFlashcard.setDifficulty(flashcard.getDifficulty());

        return flashcardRepository.save(dbFlashcard);
    }

    public void deleteFlashcardById(Integer flashcardId){
        if(flashcardRepository.deleteByFlashcardId(flashcardId) == 0)
            throw new ResourceNotFoundException(Flashcard.class, "flashcardId", flashcardId);
    }
}
