package ru.job4j.social.media.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends ListCrudRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Post> findByOrderByCreatedDesc(Pageable pageable);
}
