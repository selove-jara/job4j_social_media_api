package ru.job4j.social.media.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.social.media.api.model.Post;
import ru.job4j.social.media.api.model.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int userId;
    private String username;
    private List<Post> posts;

    public UserDTO(User user, List<Post> posts) {
        this.userId = user.getId();
        this.username = user.getFullName();
        this.posts = posts;
    }
}
