package com.tinderrestaurant.Tinder.Restaurants.service;

import com.tinderrestaurant.Tinder.Restaurants.model.Order;

public interface OrderService extends RepositoryService<Order> {
  Order addDishToOrder(Long orderId, Long dishId);

  Order payOrder(Long orderId);
}
