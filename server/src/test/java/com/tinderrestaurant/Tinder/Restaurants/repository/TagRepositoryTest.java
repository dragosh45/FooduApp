package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.Login;
import com.tinderrestaurant.Tinder.Restaurants.model.Tag;
import com.tinderrestaurant.Tinder.Restaurants.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class TagRepositoryTest {
  @Autowired private TagRepository tagRepository;

  private Tag tag;
  private User user;

  @Before
  public void setUp() {
    user =
        new User(
            "Rahaf",
            "Aln",
            "Rahaf@mail.com",
            new Login("pass123"),
            "url://photo",
            false,
            false,
            null);
    tag = new Tag("Burger");
  }

  @Test
  public void saveTagAndFindById() {
    tag = tagRepository.save(tag);
    assertThat(tagRepository.findById(tag.getId())).isEqualTo(tag);
  }

  @Test
  public void saveTagAndFindByName() {
    tag = tagRepository.save(tag);
    assertThat(tagRepository.findByName("Burger")).isEqualTo(tag);
  }

  @Test
  public void deleteTag() {
    tag = tagRepository.save(tag);
    long id = tag.getId();
    tagRepository.delete(tag);
    assertThat(tagRepository.findById(id)).isNull();
  }
}
