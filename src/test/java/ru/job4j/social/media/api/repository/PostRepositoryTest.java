package ru.job4j.social.media.api.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.model.User;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSavePostThenFindById() {
        var user = userRepository.save(
                new User(0, "user1", "test1@test.com", "test1", "UTC"));
        var post = new Post(0, "Post", "Description", LocalDateTime.now(), user);
        postRepository.save(post);
        var actualPost = postRepository.findById(post.getId());
        assertThat(actualPost).isPresent();
        assertThat(actualPost.get().getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void whenSavePostAndDeleteThenNotFound() {
        var user = userRepository.save(
                new User(0, "user1", "test1@test.com", "test1", "UTC"));
        var post = new Post(0, "Post", "Description", LocalDateTime.now(), user);
        postRepository.save(post);
        postRepository.deleteById(post.getId());
        var actualPost = postRepository.findById(post.getId());
        assertThat(actualPost).isEmpty();
        assertThat(postRepository.findAll()).isEmpty();
    }
}