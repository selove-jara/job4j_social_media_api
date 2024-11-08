package ru.job4j.social.media.api.model;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "fullName не может быть пустым")
    @Length(min = 3,
            message = "fullName должно быть не менее 3")
    private String fullName;
    @Length(min = 6,
            max = 25,
            message = "email должно быть не менее 6 и не более 25 символов")
    private String email;

    private String password;

    private String timezone;
}