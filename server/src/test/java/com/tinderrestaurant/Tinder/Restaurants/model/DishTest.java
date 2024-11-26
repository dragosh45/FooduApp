package com.tinderrestaurant.Tinder.Restaurants.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DishTest {
  @Rule public ExpectedException thrown = ExpectedException.none();
  @Autowired private TestEntityManager entityManager;
  private Dish dish;
  private Restaurant restaurant;
  private Category category;
  private User user;

  @Before
  public void setup() {
    this.category = new Category("dinner");
    this.user =
        new User(
            "Dragos",
            "Preda",
            "dragos@mail.com",
            new Login("bla123"),
            "url://photo100",
            false,
            false,
            null);
    this.restaurant = new Restaurant("friends", "friends_logo", "phone", true, null, this.user);
    this.dish = new Dish(1L, "kebab", restaurant, category);
  }

  @Test
  public void saveShouldPersistData() {
    Dish dummy = this.entityManager.persist(this.dish);
    assertThat(dummy.getName()).isEqualTo(this.dish.getName());
  }
}
