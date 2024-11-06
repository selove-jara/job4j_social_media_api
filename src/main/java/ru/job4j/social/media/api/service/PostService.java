package ru.job4j.social.media.api.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.social.media.api.model.*;

public interface PostService {
    @Transactional
    void create(Post post);

    @Transactional
    boolean updatePost(int postId, String title, String description);

    @Transactional
    boolean deletePost(int postId);

}
