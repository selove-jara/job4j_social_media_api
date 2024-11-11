package ru.job4j.social.media.api.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.social.media.api.model.Subscription;
import ru.job4j.social.media.api.model.User;
import ru.job4j.social.media.api.security.models.ERole;
import ru.job4j.social.media.api.security.models.Role;
import ru.job4j.social.media.api.security.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscriptionRepositoryTest {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
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
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void whenSaveSubscriptionThenFindById() {
        var userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
        var follower = userRepository.save(new User(0, "Follower", "follower@test.com", "pass", "UTC", Set.of(userRole)));
        var following = userRepository.save(new User(0, "Following", "following@test.com", "pass", "UTC", Set.of(userRole)));
        var subscription = new Subscription(0, follower, following, LocalDateTime.now(), false, false);
        subscriptionRepository.save(subscription);

        var actualSubscription = subscriptionRepository.findById(subscription.getId());
        assertThat(actualSubscription).isPresent();
        assertThat(actualSubscription.get().getFollower().getId()).isEqualTo(follower.getId());
        assertThat(actualSubscription.get().getFollowing().getId()).isEqualTo(following.getId());
    }

    @Test
    public void whenSaveSubscriptionAndDeleteThenNotFound() {
        var userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
        var follower = userRepository.save(new User(0, "Follower", "follower@test.com", "pass", "UTC", Set.of(userRole)));
        var following = userRepository.save(new User(0, "Following", "following@test.com", "pass", "UTC", Set.of(userRole)));
        var subscription = new Subscription(0, follower, following, LocalDateTime.now(), false, false);
        subscriptionRepository.save(subscription);

        subscriptionRepository.deleteById(subscription.getId());
        var actualSubscription = subscriptionRepository.findById(subscription.getId());
        assertThat(actualSubscription).isEmpty();
        assertThat(subscriptionRepository.findAll()).isEmpty();
    }
}