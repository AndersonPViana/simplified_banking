package com.simplifiedBanking.repositories;

import com.simplifiedBanking.domain.user.User;
import com.simplifiedBanking.domain.user.UserType;
import com.simplifiedBanking.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findUserByDocumentCase1() {
        String document = "19345657538";
        UserDTO data = new UserDTO("Anderson", "test", document,"test@test.com", "123456", new BigDecimal(40), UserType.COMMAN);
        this.createUser(data);

        Optional<User> user = repository.findUserByDocument(document);

        assertThat(user.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User from DB when not exists")
    void findUserByDocumentCase2() {
        String document = "19345657538";
        Optional<User> user = repository.findUserByDocument(document);

        assertThat(user.isEmpty()).isTrue();
    }

    private User createUser(UserDTO data){
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}