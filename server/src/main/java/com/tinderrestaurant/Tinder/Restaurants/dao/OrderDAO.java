package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.exception.NoPermissionForResourceException;
import com.tinderrestaurant.Tinder.Restaurants.exception.ResourceUnavailableException;
import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.model.Order;
import com.tinderrestaurant.Tinder.Restaurants.model.User;
import com.tinderrestaurant.Tinder.Restaurants.repository.DishRepository;
import com.tinderrestaurant.Tinder.Restaurants.repository.OrderRepository;
import com.tinderrestaurant.Tinder.Restaurants.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/** retrieving/storing orders from/to database */
@Service
public class OrderDAO implements OrderService {

  private final OrderRepository orderRepository;
  private final DishRepository dishRepository;

  @Autowired
  public OrderDAO(OrderRepository orderRepository, DishRepository dishRepository) {
    this.orderRepository = orderRepository;
    this.dishRepository = dishRepository;
  }

  /**
   * finds all orders
   *
   * @return the list of all orders
   */
  @Override
  public List<Order> findAll() {
    return orderRepository.findAll();
  }

  /**
   * saving the order to database
   *
   * @param order order (to be saved)
   * @return saved order
   */
  @Override
  public Order save(Order order) {
    return this.orderRepository.save(order);
  }

  /**
   * finding the order using its id
   *
   * @param id of order
   * @return found order with the provided id
   * @throws ResourceUnavailableException if the order was not found
   */
  @Override
  public Order findById(Long id) {
    return orderRepository
        .findById(id)
        .orElseThrow(() -> new ResourceUnavailableException("Order", "id", id));
  }

  /**
   * removing the order form the database after finding it using its id and after confirming that
   * the user is authorised to delete
   *
   * @param id of order
   * @throws ResourceUnavailableException if the order was not found
   */
  @Override
  public void delete(Long id) {
    Order orderToDelete =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Order", "id", id));
    checkUserHasPermissions(orderToDelete.getUser(), id);

    orderRepository.deleteById(id);
  }

  /**
   * updating the order by replacing the old order with the new one and checking if the user is
   * authorised to update
   *
   * @param newOrder order (to be returned)
   * @param id of old order (to be replaced)
   * @return the saved newOrder after being replaced
   * @throws ResourceUnavailableException if the order was not found
   */
  @Override
  public Order update(Order newOrder, Long id) {
    Order found =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Order", "id", id));

    checkUserHasPermissions(found.getUser(), id);
    return orderRepository.save(replaceOrder(found, newOrder));
  }

  /**
   * checking if the user role is an admin or not and then checks the email
   *
   * @param userFromResource defining the user
   * @param orderId of order (to check the permission for)
   * @throws NoPermissionForResourceException if the user is not authorised to do this task
   */
  private void checkUserHasPermissions(User userFromResource, Long orderId) {
    if (!userFromResource.getRole().getName().equals("ADMIN")
        && !SecurityContextHolder.getContext()
            .getAuthentication()
            .getName()
            .equals(userFromResource.getEmail())) {
      throw new NoPermissionForResourceException("Order", "id", orderId);
    }
  }

  /**
   * replacing the old order with a new one by setting its id, user, and restaurant to the new order
   *
   * @param oldOrder old order (to be changed)
   * @param newOrder new order to get all oldOrder info
   * @return new order with the new info
   */
  private Order replaceOrder(Order oldOrder, Order newOrder) {
    newOrder.setId(oldOrder.getId());
    newOrder.setUser(oldOrder.getUser());
    newOrder.setRestaurant(oldOrder.getRestaurant());
    return newOrder;
  }

  /**
   * finding the order using its id and then add the addDishToCategory to that order, and save the
   * order
   *
   * @param orderId id of order (to be searched for)
   * @param dishId addDishToCategory (to be added)
   * @return saved order with the provided addDishToCategory
   * @throws ResourceUnavailableException if the order was not found
   */
  @Override
  public Order addDishToOrder(Long orderId, Long dishId) {
    Order order =
        this.orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceUnavailableException("Order", "id", orderId));

    Dish dish =
        dishRepository
            .findById(dishId)
            .orElseThrow(() -> new ResourceUnavailableException("Dish", "id", dishId));

    order.addDish(dish);
    return this.orderRepository.save(order);
  }

  @Override
  public Order payOrder(Long orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceUnavailableException("Order", "id", orderId));

    order.setPaid(true);
    return orderRepository.save(order);
  }
}
