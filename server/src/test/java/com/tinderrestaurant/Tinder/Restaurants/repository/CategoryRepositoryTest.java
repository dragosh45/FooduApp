package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.Category;
import com.tinderrestaurant.Tinder.Restaurants.model.Login;
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
public class CategoryRepositoryTest {
  @Autowired private CategoryRepository categoryRepository;

  private Category category;
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
    category = new Category("Indian");
  }

  @Test
  public void saveCategoryAndFindById() {
    category = categoryRepository.save(category);
    assertThat(categoryRepository.findById(category.getId())).isEqualTo(category);
  }

  @Test
  public void saveCategoryAndFindByName() {
    category = categoryRepository.save(category);
    assertThat(categoryRepository.findByName("Indian")).isEqualTo(category);
  }

  @Test
  public void deleteCategory() {
    category = categoryRepository.save(category);
    long id = category.getId();
    categoryRepository.delete(category);
    assertThat(categoryRepository.findById(id)).isNull();
  }
}
