package ru.job4j.social.media.api.service;


import org.springframework.transaction.annotation.Transactional;
import ru.job4j.social.media.api.dto.UserDTO;
import ru.job4j.social.media.api.model.*;

import java.util.List;
import java.util.Optional;

public interface PostService {
    @Transactional
    void save(Post post);

    @Transactional
    boolean updatePost(int postId, String title, String description);

    @Transactional
    boolean deletePost(int postId);

    @Transactional
    boolean update(Post post);

    @Transactional
    Optional<Post> findById(int id);

    @Transactional
    List<Post> findAll();

    @Transactional
    boolean deleteById(int id);

    @Transactional
    List<UserDTO> getPostsByUserId(List<Integer> userIds);
}
