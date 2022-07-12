package com.revature.flashbash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "flashcards")
@Getter
@Setter
@RequiredArgsConstructor
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer flashcardId;

    private String question;
    private String answer;

    @Enumerated
    private Topic topic;

    // what relationship does User have with Flashcard? -> User (One) to Flashcard (Many)
    @ManyToOne
    private User creator;
}
