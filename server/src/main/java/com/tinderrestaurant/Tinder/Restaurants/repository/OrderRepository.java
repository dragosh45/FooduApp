package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  //returns list of not processed orders
  List<Order> findOrderByProcessedFalseAndRejectedFalse();

  //returns list of processed orders and unpaid orders
  List<Order> findOrderByProcessedTrueAndRejectedFalse();
}
