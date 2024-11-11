package ru.job4j.social.media.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.service.PostService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
@Tag(name = "PostController", description = "PostController management APIs")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "Retrieve a Post by postId",
            description = "Get a Post object by specifying its postId.",
            tags = { "Post", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId") int userId) {
        return postService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @Operation(
            summary = "Create a new Post",
            description = "Save a new Post object.",
            tags = { "Post", "create" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post created successfully", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid Post data", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping
    public ResponseEntity<Post> save(@Valid @RequestBody Post post) {
        postService.save(post);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @Operation(
            summary = "Update an existing Post",
            description = "Update an existing Post object.",
            tags = { "Post", "update" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody Post post) {
        if (postService.update(post)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@Valid @RequestBody Post post) {
        postService.update(post);
    }

    @Operation(
            summary = "Delete a Post by postId",
            description = "Delete a Post object by specifying its postId.",
            tags = { "Post", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Post deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable int postId) {
        if (postService.deleteById(postId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
