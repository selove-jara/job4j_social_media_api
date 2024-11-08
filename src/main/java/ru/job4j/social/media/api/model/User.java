package ru.job4j.social.media.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User Model Information")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "fullName не может быть пустым")
    @Length(min = 3,
            message = "fullName должно быть не менее 3")
    @Schema(description = "FullName title", example = "Ivan Ivanov")
    private String fullName;
    @Length(min = 6,
            max = 25,
            message = "email должно быть не менее 6 и не более 25 символов")
    @Schema(description = "Email address of the user", example = "ivan.ivanov@example.com")
    private String email;
    @Schema(description = "Password for the user account", example = "Password123")
    private String password;
    @Schema(description = "Time zone of the user in  format", example = "UTC")
    private String timezone;
}