package ru.job4j.social.media.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.job4j.social.media.api.dto.UserDTO;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.service.PostService;
import ru.job4j.social.media.api.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
@Tag(name = "PostsController", description = "Posts management APIs")
public class PostsController {

    private final PostService postService;
    private final UserService userService;

    @Operation(
            summary = "Retrieve all posts",
            description = "Get a list of all posts available in the system. The response is a list of Post objects.",
            tags = { "Posts", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAll() {
        return postService.findAll();
    }


    @Operation(
            summary = "Retrieve posts by user IDs",
            description = "Get posts created by specific users by providing their user IDs. The response is a list of UserDTO objects containing posts for each user.",
            tags = { "Posts", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid user ID provided", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getUsersPosts(@RequestParam List<Integer> userIds) {
        List<UserDTO> userPosts = postService.getPostsByUserId(userIds);
        return ResponseEntity.ok(userPosts);
    }
}
