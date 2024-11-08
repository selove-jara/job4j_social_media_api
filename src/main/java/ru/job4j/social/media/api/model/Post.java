package ru.job4j.social.media.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model representing a user post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @NotBlank(message = "Название не может быть пустым")
    @Length(min = 6,
            max = 15,
            message = "Название должно быть не менее 6 и не более 15 символов")
    @Schema(description = "Title of the post", example = "My First Post")
    private String title;

    @Schema(description = "Description or content of the post", example = "This is a detailed description of the post.")
    private String description;

    @Schema(description = "Date and time when the post was created", example = "2024-11-08T15:30:00")
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false)
    @Schema(description = "User who created the post", example = "User object representing the post author")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "post_file",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"))
    @Schema(description = "List of images associated with the post", example = "[PostImage objects]")
    private List<PostImage> images;
}
