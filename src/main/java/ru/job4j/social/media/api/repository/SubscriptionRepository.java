package ru.job4j.social.media.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.social.media.api.model.Subscription;
import ru.job4j.social.media.api.model.User;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

    @Query("SELECT s.follower FROM Subscription s WHERE s.user.id = :userId")
    List<User> findAllFollowers(@Param("userId") int userId);

    @Query("SELECT s.following FROM Subscription s JOIN s.follower f WHERE f.id = :userId")
    List<User> findAllFriends(@Param("userId") int userId);
}
