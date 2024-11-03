package ru.job4j.social.media.api.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.social.media.api.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void setUp() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveUserThenFindById() {
        var user = new User(0, "Moroz", "Moroz@example.com", "password123", "UTC");
        var savedUser = userRepository.save(user);

        var actualUser = userRepository.findById(savedUser.getId());
        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getFullName()).isEqualTo(user.getFullName());
        assertThat(actualUser.get().getEmail()).isEqualTo(user.getEmail());
        assertThat(actualUser.get().getPassword()).isEqualTo(user.getPassword());
        assertThat(actualUser.get().getTimezone()).isEqualTo(user.getTimezone());
    }

    @Test
    public void whenSaveUserAndDeleteThenNotFound() {
        var user = new User(0, "Moroz", "Moroz@example.com", "password123", "UTC");
        var savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        var actualUser = userRepository.findById(savedUser.getId());
        assertThat(actualUser).isEmpty();
        assertThat(userRepository.findAll()).isEmpty();
    }
}