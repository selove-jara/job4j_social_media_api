package ru.job4j.social.media.api.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.social.media.api.model.Message;
import ru.job4j.social.media.api.model.User;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageRepositoryTest {
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
    public void whenSaveMessageThenFindById() {
        var user1 = userRepository.save(
                new User(0, "user1", "test1@test.com", "test1", "UTC"));
        var user2 = userRepository.save(
                new User(0, "user2", "test2@test.com", "test2", "UTC"));
        var message = new Message(0, user1, user2, "message", LocalDateTime.now());
        messageRepository.save(message);
        var actualMessage = messageRepository.findById(message.getId());
        assertThat(actualMessage).isPresent();
        assertThat(actualMessage.get().getMessage()).isEqualTo(message.getMessage());
    }

    @Test
    public void whenSaveMessageAndDeleteThenNotFound() {
        var user1 = userRepository.save(
                new User(0, "user1", "test1@test.com", "test1", "UTC"));
        var user2 = userRepository.save(
                new User(0, "user2", "test2@test.com", "test2", "UTC"));
        var message = new Message(0, user1, user2, "message", LocalDateTime.now());
        messageRepository.save(message);
        messageRepository.deleteById(message.getId());
        var actualMessage = messageRepository.findById(message.getId());
        assertThat(actualMessage).isEmpty();
        assertThat(messageRepository.findAll()).isEmpty();
    }
}