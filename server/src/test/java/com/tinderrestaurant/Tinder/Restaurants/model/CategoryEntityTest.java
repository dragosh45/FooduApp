package com.tinderrestaurant.Tinder.Restaurants.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class CategoryEntityTest {
  @Autowired private TestEntityManager entityManager;

  @Rule public ExpectedException thrown = ExpectedException.none();

  private Category category;
  private User user;

  @Before
  public void setup() {
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
  public void saveCategory() {
    Category savedCategory = entityManager.persistAndFlush(category);
    assertThat(savedCategory.getName().equals("Indian"));
  }

  @Test
  public void createCategoryNullNameException() throws IllegalArgumentException {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Category name should not be empty");
    new Category(null);
  }
}
