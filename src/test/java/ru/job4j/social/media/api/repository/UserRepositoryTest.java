package ru.job4j.social.media.api.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.social.media.api.model.User;
import ru.job4j.social.media.api.security.models.ERole;
import ru.job4j.social.media.api.security.models.Role;
import ru.job4j.social.media.api.security.repository.RoleRepository;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUpRoles() {
        roleRepository.findByName(ERole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(null, ERole.ROLE_USER)));
    }

    @AfterEach
    public void setUp() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void whenSaveUserThenFindById() {
        var userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
        var user = new User(0, "Moroz", "Moroz@example.com", "password123", "UTC", Set.of(userRole));
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
        var userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
        var user = new User(0, "Moroz", "Moroz@example.com", "password123", "UTC", Set.of(userRole));
        var savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        var actualUser = userRepository.findById(savedUser.getId());
        assertThat(actualUser).isEmpty();
        assertThat(userRepository.findAll()).isEmpty();
    }
}