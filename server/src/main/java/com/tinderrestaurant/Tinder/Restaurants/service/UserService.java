package com.tinderrestaurant.Tinder.Restaurants.service;

import com.tinderrestaurant.Tinder.Restaurants.model.Location;
import com.tinderrestaurant.Tinder.Restaurants.model.Order;
import com.tinderrestaurant.Tinder.Restaurants.model.Review;
import com.tinderrestaurant.Tinder.Restaurants.model.User;
import com.tinderrestaurant.Tinder.Restaurants.model.security.JwtBody;
import com.tinderrestaurant.Tinder.Restaurants.model.security.SecurityUser;

import java.util.List;

public interface UserService extends RepositoryService<User> {

  //setting the role of a user and his/her login details, and save them
  User create(User user, final String role);

  //adding location to user and save it to database
  User addLocation(Location location);

  //returns user's saved info form database
  User getProfile();

  //return user found by email
  User findByEmail(String email);

  //returns user after updating his/her context and saving it to database
  User updateByContext(User user);

  //returns list of orders from database for user
  List<Order> getOrders();

  //returns repository if the user's email is authenticated
  User findAuthenticated();

  //authenticating users
  JwtBody authenticateUser(SecurityUser user);

  /**
   * get all user's reviews
   *
   * @return  user reviews
   */
  List<Review> getReviews();
}
