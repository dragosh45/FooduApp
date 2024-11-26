package com.tinderrestaurant.Tinder.Restaurants.controller;

import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.model.Order;
import com.tinderrestaurant.Tinder.Restaurants.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/** this class is to control the order status. */
@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }


  @GetMapping
  public List<Order> getAllOrders() {
    return orderService.findAll();
  }

  @GetMapping("/{order_id}")
  public Order FindOrderById(@PathVariable(value = "order_id") Long orderId) {
    return orderService.findById(orderId);
  }

  @PostMapping("/{order_id}/pay")
  public Order payOrder(@PathVariable(value = "order_id") Long orderId) {
    return orderService.payOrder(orderId);
  }

  /**
   * adding a addDishToCategory to the order
   *
   * @param orderId of addDishToCategory
   * @param dishId addDishToCategory (to be added)
   * @return order after adding the addDishToCategory
   */

  @PostMapping("/{order_id}/dish/{dish_id}")
  public Order addDishToOrder(
      @PathVariable(name = "order_id") Long orderId, @PathVariable(name = "dish_id") Long dishId) {
    return this.orderService.addDishToOrder(orderId, dishId);
  }

  /**
   * deleting order from the system
   *
   * @param id of order
   */
  @DeleteMapping("/{id}")
  public void cancelOrder(@PathVariable(value = "id") Long id) {
    this.orderService.delete(id);
  }

  /**
   * modify the order
   *
   * @param id of order
   * @param order order (to be updated)
   */
  @PutMapping("/{id}")
  public void updateOrder(@PathVariable(value = "id") Long id, @Valid @RequestBody Order order) {
    this.orderService.update(order, id);
  }
}
