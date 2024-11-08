package ru.job4j.social.media.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.social.media.api.dto.UserDTO;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.service.PostService;
import ru.job4j.social.media.api.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;
    private final UserService userService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAll() {
        return postService.findAll();
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getUsersPosts(@RequestParam List<Integer> userIds) {
        List<UserDTO> userPosts = postService.getPostsByUserId(userIds);
        return ResponseEntity.ok(userPosts);
    }
}
