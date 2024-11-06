package ru.job4j.social.media.api.service;

import org.springframework.stereotype.Service;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.model.User;
import ru.job4j.social.media.api.repository.PostRepository;

import java.util.Optional;

@Service
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    public SimplePostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public void create(Post post) {
        postRepository.save(post);
    }

    @Override
    public boolean updatePost(int postId, String title, String description) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            postRepository.updatePostTitleAndDescription(postId, title, description);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePost(int postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            postRepository.deletePostById(postId);
            return true;
        }
        return false;
    }
}
