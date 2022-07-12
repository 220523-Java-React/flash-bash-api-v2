package com.revature.flashbash.service;

import com.revature.flashbash.exception.ResourceNotFoundException;
import com.revature.flashbash.model.Flashcard;
import com.revature.flashbash.model.Flashcard.Topic;
import com.revature.flashbash.repository.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;

    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public Flashcard createFlashcard(Flashcard flashcard){
        return flashcardRepository.save(flashcard);
    }

    public List<Flashcard> getAllFlashcards(){
        return flashcardRepository.findAll();
    }

    public Flashcard getFlashcardById(Integer flashcardId){
        return flashcardRepository.findById(flashcardId).orElseThrow(() -> new ResourceNotFoundException(Flashcard.class, "flashcardId", flashcardId));
    }

    public List<Flashcard> getAllFlashcardsByCreatorId(Integer creatorId){
        return flashcardRepository.findAllByCreator_UserId(creatorId);
    }

    public List<Flashcard> getAllFlashcardsByCreatorUsername(String username){
        return flashcardRepository.findAllByCreator_Username(username);
    }

    public List<Flashcard> getAllFlashcardsByTopic(Topic topic){
        return flashcardRepository.findAllByTopic(topic);
    }

    public Flashcard updateFlashcard(Flashcard flashcard){
        if(!flashcardRepository.existsById(flashcard.getFlashcardId())){
            throw new ResourceNotFoundException(Flashcard.class, "flashcardId", flashcard.getFlashcardId());
        }

        return flashcardRepository.save(flashcard);
    }

    public void deleteFlashcardById(Integer flashcardId){
        if(flashcardRepository.deleteByFlashcardId(flashcardId) == 0) throw new ResourceNotFoundException(Flashcard.class, "flashcardId", flashcardId);
    }
}
