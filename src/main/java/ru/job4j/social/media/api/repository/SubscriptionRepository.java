package ru.job4j.social.media.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.social.media.api.model.Subscription;
import ru.job4j.social.media.api.model.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

    @Query("SELECT s.follower FROM Subscription s WHERE s.following.id = :userId AND s.isSubscriber = true")
    List<User> findAllFollowers(@Param("userId") int userId);

    @Query("SELECT s.following FROM Subscription s WHERE s.follower.id = :userId AND s.isFriend = true")
    List<User> findAllFriends(@Param("userId") int userId);

    @Query("SELECT s FROM Subscription s WHERE s.follower.id = :followerId AND s.following.id = :followingId")
    Optional<Subscription> findSubscriptionBetween(@Param("followerId") int followerId, @Param("followingId") int followingId);

    List<Subscription> findByFollowerOrFollowing(User follower, User following);
}
