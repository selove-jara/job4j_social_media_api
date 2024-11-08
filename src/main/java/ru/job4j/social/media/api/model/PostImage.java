package ru.job4j.social.media.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "files")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model representing an image associated with a post")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Название не может быть пустым")
    @Length(min = 6,
            max = 10,
            message = "Название должно быть не менее 6 и не более 10 символов")
    @Schema(description = "Name of the image file", example = "image1")
    private String name;

    @NotBlank(message = "Путь не может быть пустым")
    @Schema(description = "Path where the image file is stored", example = "/images/posts/image1.jpg")
    private String path;
}