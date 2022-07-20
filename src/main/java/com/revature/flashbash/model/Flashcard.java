package com.revature.flashbash.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "flashcards")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flashcardId;

    private String question;
    private String answer;

    @Enumerated
    private Topic topic;

    @Enumerated
    private Difficulty difficulty;

    // what relationship does User have with Flashcard? -> User (One) to Flashcard (Many)
    @ManyToOne
    private User creator;

    public enum Topic {
        JAVA, SQL, HTML, CSS, JAVASCRIPT, SPRING, DEVOPS
    }

    public enum Difficulty {
        NOVICE, APPRENTICE, ADEPT, EXPERT, MASTER;
    }

    public enum SortBy{
        ID,
        QUESTION,
        ANSWER,
        TOPIC,
        CREATOR;
    }
}
