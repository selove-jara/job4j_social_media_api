package ru.job4j.social.media.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.social.media.api.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT user FROM User user WHERE user.email = :email AND user.password = :password")
    Optional<User> findByLoginAndPassword(@Param("email") String email, @Param("password") String password);

}
