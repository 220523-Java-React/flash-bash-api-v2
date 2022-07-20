package com.revature.flashbash.controller;

import com.revature.flashbash.model.Flashcard;
import com.revature.flashbash.model.Order;
import com.revature.flashbash.model.User;
import com.revature.flashbash.service.FlashcardService;
import com.revature.flashbash.util.PaginationOptions;
import com.revature.flashbash.util.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }


    @GetMapping
    public Page<Flashcard> getAllFlashcards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") Flashcard.SortBy sort,
            @RequestParam(defaultValue = "ASCENDING") Order order,
            @RequestParam(required = false) Integer creator,
            @RequestParam(required = false) Flashcard.Topic topic,
            @RequestParam(required = false) Flashcard.Difficulty difficulty
            ) {

        return flashcardService.getAllFlashcards(
                new PaginationOptions(page, size, sort, order),
                new SearchCriteria(){{
                    put(User.class, creator);
                    put(Flashcard.Topic.class, topic);
                    put(Flashcard.Difficulty.class, difficulty);
                }});
    }

    @GetMapping("/{flashcardId}")
    public Flashcard getFlashcardById(@PathVariable Integer flashcardId){
        return flashcardService.getFlashcardById(flashcardId);
    }

    @PostMapping
    public Flashcard createNewFlashcard(@RequestBody Flashcard flashcard){
        return flashcardService.createFlashcard(flashcard);
    }

    @PutMapping("/{flashcardId}")
    public Flashcard replaceFlashcard(@RequestBody Flashcard flashcard, @PathVariable Integer flashcardId){
        return flashcardService.replaceFlashcard(flashcard, flashcardId);
    }

    @PatchMapping("/{flashcardId}")
    public Flashcard updateFlashcard(@RequestBody Flashcard flashcard, @PathVariable Integer flashcardId){
        return flashcardService.updateFlashcard(flashcard, flashcardId);
    }

    @DeleteMapping("/{flashcardId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFlashcardById(@PathVariable Integer flashcardId){
        flashcardService.deleteFlashcardById(flashcardId);
    }

    @GetMapping("/topics")
    public Flashcard.Topic[] getAllFlashcardTopics(){
        return Flashcard.Topic.values();
    }

    @GetMapping("/difficulties")
    public Flashcard.Difficulty[] getAllFlashcardDifficulties(){
        return Flashcard.Difficulty.values();
    }

    @GetMapping("/sort")
    public Flashcard.SortBy[] getAllFlashcardSortOptions(){
        return Flashcard.SortBy.values();
    }
}
