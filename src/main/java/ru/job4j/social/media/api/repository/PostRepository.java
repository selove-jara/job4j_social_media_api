package ru.job4j.social.media.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends ListCrudRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Post> findByOrderByCreatedDesc(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.title = :title, p.description = :description WHERE p.id = :postId")
    int updatePostTitleAndDescription(@Param("postId") int postId,
                                      @Param("title") String title,
                                      @Param("description") String description);

    @Modifying
    @Query(value = "DELETE FROM post_file WHERE post_id = :postId AND file_id = :fileId", nativeQuery = true)
    int deleteByPostIdAndFileId(int postId, int fileId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Post p WHERE p.id = :postId")
    int deletePostById(@Param("postId") int postId);

    @Query("SELECT p FROM Post p "
            + "JOIN Subscription s ON p.user.id = s.following.id "
            + "WHERE s.follower.id = :userId ORDER BY p.created DESC")
    Page<Post> findPostsByFollowers(@Param("userId") int userId, Pageable pageable);
}
