package ru.job4j.social.media.api.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.social.media.api.model.Subscription;
import ru.job4j.social.media.api.model.User;
import ru.job4j.social.media.api.repository.SubscriptionRepository;
import ru.job4j.social.media.api.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleSubscribeService implements SubscribeService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendFriendRequest(int followerId, int followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("Following not found"));

        Optional<Subscription> subscriptions = subscriptionRepository.findSubscriptionBetween(followerId, followingId);

        if (subscriptions.isEmpty()) {
            Subscription subscription = new Subscription();
            subscription.setFollower(follower);
            subscription.setFollowing(following);
            subscription.setCreatedAt(LocalDateTime.now());
            subscription.setSubscriber(true);
            subscription.setFriend(false);
            subscriptionRepository.save(subscription);
        }
    }

    @Transactional
    public void acceptFriendRequest(int followerId, int followingId) {
        Subscription subscription = subscriptionRepository.findSubscriptionBetween(followerId, followingId)
                .orElseThrow(() -> new IllegalArgumentException("Запрос не найден"));

        subscription.setFriend(true);
        subscriptionRepository.save(subscription);

        Subscription reverseSubscription = new Subscription();
        reverseSubscription.setFollower(subscription.getFollowing());
        reverseSubscription.setFollowing(subscription.getFollower());
        reverseSubscription.setCreatedAt(LocalDateTime.now());
        reverseSubscription.setSubscriber(true);
        reverseSubscription.setFriend(true);
        subscriptionRepository.save(reverseSubscription);
    }

    @Transactional
    public void declineFriendRequest(int followerId, int followingId) {
        Subscription subscription = subscriptionRepository.findSubscriptionBetween(followerId, followingId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка в друзья не найдена"));
        subscription.setFriend(false);
        subscriptionRepository.save(subscription);
    }

    @Transactional
    public void removeFriend(int userId, int friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Друг не найден"));
        subscriptionRepository.findByFollowerOrFollowing(user, friend).forEach(subscription -> {
            if (subscription.getFollower().equals(user)) {
                subscription.setFriend(false);
                subscriptionRepository.save(subscription);
            } else {
                subscriptionRepository.delete(subscription);
            }
        });
    }
}

