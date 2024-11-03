package ru.job4j.social.media.api.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.social.media.api.model.PostImage;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostImageRepositoryTest {
    @Autowired
    private PostImageRepository postImageRepository;

    @AfterEach
    public void setUp() {
        postImageRepository.deleteAll();
    }

    @Test
    public void whenSavePostImage_thenFindById() {
        var image = new PostImage(0, "Image1", "/images/image1");
        postImageRepository.save(image);
        var foundImage = postImageRepository.findById(image.getId());
        assertThat(foundImage).isPresent();
        assertThat(foundImage.get().getName()).isEqualTo("Image1");
    }

    @Test
    public void whenFindAll_thenReturnAllPostImages() {
        var image1 = new PostImage(0, "Image1", "/images/image1");
        var image2 = new PostImage(0, "Image2", "/images/image2");
        postImageRepository.save(image1);
        postImageRepository.save(image2);
        var images = postImageRepository.findAll();
        assertThat(images).hasSize(2);
    }
}