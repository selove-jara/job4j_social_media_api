package ru.job4j.social.media.api.security.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private HttpStatus status;
    private String message;
}
