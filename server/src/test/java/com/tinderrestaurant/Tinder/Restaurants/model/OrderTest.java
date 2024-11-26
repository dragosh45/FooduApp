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

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderTest {

  @Rule public ExpectedException thrown = ExpectedException.none();
  @Autowired private TestEntityManager entityManager;
  private Order order;
  private User user;
  private Restaurant restaurant;

  @Before
  public void setup() {
    Role role = new Role("CUSTOMER");
    this.user =
        new User(
            "Emil",
            "Lozev",
            "emil@mail.com",
            new Login("pass123"),
            "url://photo",
            false,
            false,
            null);
    this.user.setRole(role);
    this.restaurant = new Restaurant("REST restaurant", "logo", "phone", true, null, this.user);

    this.order =
        new Order(
            2,
            new Date(System.currentTimeMillis() + 1000 * 60 * 30), // 30 minutes
            this.user,
            this.restaurant);
  }

  @Test
  public void createWhenServeDateIsNullShouldThrowException() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Serve time must be provided");

    this.order =
        new Order(
            2,
            null, // 30 minutes
            this.user,
            this.restaurant);

    this.entityManager.persist(this.order);
  }

  @Test
  public void createWhenUserIsNullShouldThrowException() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User must not be null");

    this.order =
        new Order(
            2,
            new Date(System.currentTimeMillis() + 1000 * 60 * 30), // 30 minutes
            null,
            this.restaurant);

    this.entityManager.persist(this.order);
  }

  @Test
  public void createWhenRestaurantIsNullShouldThrowException() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Restaurant must not be null");

    this.order =
        new Order(
            2,
            new Date(System.currentTimeMillis() + 1000 * 60 * 30), // 30 minutes
            this.user,
            null);

    this.entityManager.persist(this.order);
  }

  @Test
  public void saveShouldPersistData() {
    Order dummy = this.entityManager.persist(this.order);
    assertThat(dummy.getSeats()).isEqualTo(this.order.getSeats());
    assertThat(dummy.getUser()).isEqualTo(this.user);
    assertThat(dummy.getRestaurant()).isEqualTo(this.restaurant);
  }
}
