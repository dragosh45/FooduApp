package com.tinderrestaurant.Tinder.Restaurants.service;

import com.tinderrestaurant.Tinder.Restaurants.model.*;

import java.util.List;

public interface RestaurantService extends RepositoryService<Restaurant> {
  // setting the user and the restaurant that was ordered from
  // then save the order to database
  Order order(Order o, Long restaurantId);

  // getting the list of orders from the restaurant and check its status
  List<Order> getOrders(Long id, String param);

  //returns repository after saving the addDishToCategory
  Dish dish(Dish d, Long restaurantId);

  /**
   * Get unverified restaurants
   *
   * @return - list of unverified restaurants
   */
  List<Restaurant> getUnverified();

  //Add category to a restaurant
  /**
   * Add category to a restaurant
   *
   * @param category - to be added
   * @param id - of restaurant
   * @return - restaurant with saved category
   */
  Restaurant addCategory(Category category, Long id);

  /**
   * get all restaurant's categories
   *
   * @param id - of restaurant
   * @return - restaurant's categories
   */
  List<Category> getCategoriesFromRestaurant(Long id);

  /**
   * Add review to a restaurant
   *
   * @param review - to be added
   * @param id - of restaurant
   * @return - restaurant with saved review
   */
  Restaurant addReview(Review review, Long id);

  /**
   * get all restaurant's reviews
   *
   * @param id - of restaurant
   * @return - restaurant's reviews
   */
  List<Review> getRestaurantReview(Long id);
}
