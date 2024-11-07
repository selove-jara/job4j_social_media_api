package ru.job4j.social.media.api.service;

import ru.job4j.social.media.api.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    boolean update(User user);

    Optional<User> findById(int userId);

    boolean deleteById(int userId);

    List<User> findAll();
}
