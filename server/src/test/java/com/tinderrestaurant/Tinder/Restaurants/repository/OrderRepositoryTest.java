package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

  @Autowired private OrderRepository orderRepository;

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
  public void saveOrderAndFindById() {
    this.order = this.orderRepository.save(this.order);
    assertThat(this.orderRepository.findById(this.order.getId())).hasValue(this.order);
  }

  @Test
  public void deleteOrderFindIsNull() {
    Long id = this.orderRepository.save(order).getId();
    this.orderRepository.delete(this.order);
    assertThat(this.orderRepository.findById(id)).isEmpty();
  }
}
