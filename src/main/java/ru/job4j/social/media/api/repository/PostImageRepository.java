package ru.job4j.social.media.api.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.social.media.api.model.PostImage;

public interface PostImageRepository extends CrudRepository<PostImage, Integer> {
}
