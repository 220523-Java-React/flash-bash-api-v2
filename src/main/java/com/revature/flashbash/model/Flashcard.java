package com.revature.flashbash.model;

import javax.persistence.*;

@Entity(name = "flashcards")
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer flashcardId;

    private String question;
    private String answer;

    // what relationship does User have with Flashcard? -> User (One) to Flashcard (Many)
    @ManyToOne
    private User creator;





    public Integer getFlashcardId() {
        return flashcardId;
    }

    public Flashcard setFlashcardId(Integer flashcardId) {
        this.flashcardId = flashcardId;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public Flashcard setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public Flashcard setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public Flashcard setCreator(User creator) {
        this.creator = creator;
        return this;
    }
}
