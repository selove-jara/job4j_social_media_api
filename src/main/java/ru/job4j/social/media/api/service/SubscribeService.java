package ru.job4j.social.media.api.service;

public interface SubscribeService {
    void sendFriendRequest(int followerId, int followingId);

    void acceptFriendRequest(int followerId, int followingId);

    void declineFriendRequest(int followerId, int followingId);

    void removeFriend(int userId, int friendId);
}
