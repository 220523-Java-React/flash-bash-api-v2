package com.revature.flashbash.controller;

import com.revature.flashbash.model.Flashcard;
import com.revature.flashbash.model.Flashcard.Topic;

import com.revature.flashbash.service.FlashcardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public Flashcard createNewFlashcard(@RequestBody Flashcard flashcard){
        return flashcardService.createFlashcard(flashcard);
    }

    @GetMapping
    public List<Flashcard> getAllFlashcards(){
        return flashcardService.getAllFlashcards();
    }

    @GetMapping("/{flashcardId}")
    public Flashcard getFlashcardById(@PathVariable Integer flashcardId){
        return flashcardService.getFlashcardById(flashcardId);
    }

    @GetMapping("/creator/{creatorId}")
    public List<Flashcard> getAllFlashcardsByCreatorId(@PathVariable Integer creatorId){
        return flashcardService.getAllFlashcardsByCreatorId(creatorId);
    }

    @GetMapping("/creator/username/{username}")
    public List<Flashcard> getAllFlashcardsByCreatorUsername(@PathVariable String username){
        return flashcardService.getAllFlashcardsByCreatorUsername(username);
    }

    @GetMapping("/topic/{topic}")
    public List<Flashcard> getAllFlashcardsByTopic(@PathVariable String topic){
        return flashcardService.getAllFlashcardsByTopic(Topic.valueOf(topic.toUpperCase()));
    }

    @GetMapping("/topic")
    public Topic[] getAllFlashcardTopics(){
        return Topic.values();
    }

    @PatchMapping
    public Flashcard updateFlashcard(@RequestBody Flashcard flashcard){
        return flashcardService.updateFlashcard(flashcard);
    }

    @DeleteMapping("/{flashcardId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFlashcardById(@PathVariable Integer flashcardId){
        flashcardService.deleteFlashcardById(flashcardId);
    }
}
