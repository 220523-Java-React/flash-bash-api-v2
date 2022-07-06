package com.revature.flashbash.repository;

import com.revature.flashbash.model.Flashcard;
import com.revature.flashbash.model.User;
import com.revature.flashbash.model.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlashcardRepositoryTests {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Test
    public void testSomeStuff(){
        // This user will not exist in the table
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword("TestPass");
        user.setUserType(UserType.USER);

        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion("What is JPA");
        flashcard.setAnswer("Jakarta Persistence API");
        flashcard.setCreator(user);

        flashcardRepository.save(flashcard);

        // how would I get a list of flashcards that are created by a specific user, by their username
        Flashcard result = flashcardRepository.getAllByCreator_Username(user.getUsername()).get(0);


        Assertions.assertEquals(user.getUsername(), result.getCreator().getUsername());
    }
}
