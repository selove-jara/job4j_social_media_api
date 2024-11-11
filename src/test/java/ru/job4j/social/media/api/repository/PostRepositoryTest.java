package ru.job4j.social.media.api.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.model.PostImage;
import ru.job4j.social.media.api.model.User;
import ru.job4j.social.media.api.security.models.ERole;
import ru.job4j.social.media.api.security.models.Role;
import ru.job4j.social.media.api.security.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostImageRepository postImageRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUpRoles() {
        roleRepository.findByName(ERole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(null, ERole.ROLE_USER)));
    }

    @AfterEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        postImageRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void whenSavePostThenFindById() {
        var userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
        var image = new PostImage(0, "Image1", "/images/image1");
        var user = userRepository.save(
                new User(0, "user1", "test1@test.com", "test1", "UTC", Set.of(userRole)));
        var post = new Post(0, "Post123", "Description", LocalDateTime.now(), user, List.of(image));
        postRepository.save(post);
        var actualPost = postRepository.findById(post.getId());
        assertThat(actualPost).isPresent();
        assertThat(actualPost.get().getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void whenSavePostAndDeleteThenNotFound() {
        var userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
        var user = userRepository.save(
                new User(0, "user1", "test1@test.com", "test1", "UTC", Set.of(userRole)));
        var post = new Post(0, "Post123", "Description", LocalDateTime.now(), user,  Collections.emptyList());
        postRepository.save(post);
        postRepository.deleteById(post.getId());
        var actualPost = postRepository.findById(post.getId());
        assertThat(actualPost).isEmpty();
        assertThat(postRepository.findAll()).isEmpty();
    }
}