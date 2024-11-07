package ru.job4j.social.media.api.service;

import org.springframework.stereotype.Service;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.model.User;
import ru.job4j.social.media.api.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    public SimplePostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public void save(Post post) {
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

    @Override
    public boolean update(Post post) {
        if (postRepository.existsById(post.getId())) {
            postRepository.save(post);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public boolean deleteById(int id) {
        return postRepository.deletePostById(id) > 0;
    }
}
