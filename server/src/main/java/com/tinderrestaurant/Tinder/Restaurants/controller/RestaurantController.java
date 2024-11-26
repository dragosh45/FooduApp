package com.tinderrestaurant.Tinder.Restaurants.controller;

import com.tinderrestaurant.Tinder.Restaurants.model.Category;
import com.tinderrestaurant.Tinder.Restaurants.model.Order;
import com.tinderrestaurant.Tinder.Restaurants.model.Restaurant;
import com.tinderrestaurant.Tinder.Restaurants.model.Review;
import com.tinderrestaurant.Tinder.Restaurants.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * this class to control the restaurant
 */
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

  private final RestaurantService restaurantService;

  @Autowired
  public RestaurantController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  /**
   * Get all restaurants
   *
   * @return - list of users available in the database
   */
  @GetMapping
  public List<Restaurant> getAllRestaurants() {
    return restaurantService.findAll();
  }

  /**
   * Get all unverified restaurants
   *
   * @return - list of unverified restaurants
   */
  @GetMapping("/unverified")
  public List<Restaurant> getUnverified() { return restaurantService.getUnverified(); }

  /**
   * Get restaurant by id
   *
   * @param id - user_id
   * @return - user with ID if available in the db
   */
  @GetMapping("/{id}")
  public Restaurant getRestaurantById(@PathVariable(value = "id") Long id) {
    return restaurantService.findById(id);
  }

  /**
   * Create order to restaurant with the given id
   *
   * @param id - restaurant id
   * @param order - order to be made
   * @return - JSON order
   */
  @PostMapping("/{id}/order")
  public Order orderToRestaurant(
      @PathVariable(value = "id") Long id, @Valid @RequestBody Order order) {
    System.out.println(order.toString());
    return restaurantService.order(order, id);
  }

  /**
   * get orders for restaurant by order id
   * @param id - of order
   * @param param - order name
   * @return - list of orders
   */
  @GetMapping("/{id}/order")
  public List<Order> getOrders(
      @PathVariable(value = "id") Long id,
      @RequestParam(required = false, defaultValue = "all", value = "filter") String param) {
    return restaurantService.getOrders(id, param);
  }

  /**
   * Create restaurant and save it to database
   *
   * @param restaurant  restaurant object
   * @return            created restaurant
   */
  @PostMapping
  public Restaurant saveRestaurant(@Valid @RequestBody Restaurant restaurant) {
    return restaurantService.save(restaurant);
  }

  /**
   * get the categories of a restaurant
   *
   * @param id    of restaurant
   * @return      list of categories with the provided restaurant id
   */
  @GetMapping("/{id}/category")
  public List<Category> getCategoriesFromRestaurant(@PathVariable(value = "id") Long id){
    return restaurantService.getCategoriesFromRestaurant(id);
  }

  /**
   * creating and saving category to a restaurant
   *
   * @param category    new category to be added
   * @param id          of restaurant
   * @return            restaurant with the new category
   */
  @PostMapping("/{id}/category")
  public Restaurant addCategoryToRestaurant(
      @Valid @RequestBody Category category, @PathVariable(value = "id") Long id) {
    return restaurantService.addCategory(category, id);
  }

  /**
   * updating restaurant's information
   *
   * @param id            of restaurant
   * @param restaurant    the restaurant (to be updated)
   * @return              updated restaurant
   */
  @PutMapping("/{id}")
  public Restaurant updateRestaurant(
      @PathVariable(value = "id") Long id, @Valid @RequestBody Restaurant restaurant) {
    return restaurantService.update(restaurant, id);
  }

  /**
   * deleting a restaurant using its id
   *
   * @param id    of restaurant
   */
  @DeleteMapping("/{id}")
  public void deleteRestaurant(@PathVariable(value = "id") Long id) {
    restaurantService.delete(id);
  }

  /**
   * creating and saving review to a restaurant
   *
   * @param review    new review to be added
   * @param id          of restaurant
   * @return            restaurant with the new review
   */
  @PostMapping("/{id}/review")
  public Restaurant addReviewToRestaurant(
          @Valid @RequestBody Review review, @PathVariable(value = "id") Long id) {
    return restaurantService.addReview(review, id);
  }

  /**
   * get reviews for restaurant by restaurant id
   * @param id - of restaurant
   * @return - list of reviews
   */
  @GetMapping("/{id}/review")
  public List<Review> getRestaurantReviews(
          @PathVariable(value = "id") Long id) {
    return restaurantService.getRestaurantReview(id);
  }
}
