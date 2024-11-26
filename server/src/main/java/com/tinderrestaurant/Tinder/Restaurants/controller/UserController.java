package com.tinderrestaurant.Tinder.Restaurants.controller;

import com.tinderrestaurant.Tinder.Restaurants.model.Location;
import com.tinderrestaurant.Tinder.Restaurants.model.Order;
import com.tinderrestaurant.Tinder.Restaurants.model.Review;
import com.tinderrestaurant.Tinder.Restaurants.model.User;
import com.tinderrestaurant.Tinder.Restaurants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tinderrestaurant.Tinder.Restaurants.security.SecurityConstants.ROLE_OWNER;
import static com.tinderrestaurant.Tinder.Restaurants.security.SecurityConstants.ROLE_USER;

/**
 * UserController is responsible for providing endpoints to the REST-ful API. In this case, the
 * class handles any HTTP requests to the /user endpoint. GET http://localhost:8080/user will be
 * handled by getAllUser() method and so on.
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userDAO) {
    this.userService = userDAO;
  }

  /**
   * create authentication
   *
   * @return  user's authentication status
   */
  @GetMapping
  public User getAuthenticated() {
    return userService.findAuthenticated();
  }

  /**
   * finding all orders
   *
   * @return    list of orders
   */
  @GetMapping("/orders")
  public List<Order> getAllOrders() {
    return userService.getOrders();
  }

  /**
   * finding all reviews
   *
   * @return    list of reviews
   */
  @GetMapping("/reviews")
  public List<Review> getAllReviews() {
    return userService.getReviews();
  }

  /**
   * find a user by id
   *
   * @param userId    id of user (to be searched for)
   * @return          user with this id
   */
  @GetMapping("/{id}")
  public User getUserById(@PathVariable(value = "id") Long userId) {
    return userService.findById(userId);
  }

  /**
   * get the user information e.g., name, surname and email
   *
   * @return    user's information
   */
  @GetMapping("/profile")
  public User getUserProfile() {
    return userService.getProfile();
  }

  /** update user's profile
   *
   * @param location    of user
   * @return            user with location added to his/her profile
   */
  @PostMapping("/location")
  public User postLocation(@RequestBody Location location) {
    return userService.addLocation(location);
  }

  /**
   * add new user to the database
   *
   * @param user    user's role and his/her info
   * @return        new user
   */
  @PostMapping("/sign-up")
  public User createUser(@Valid @RequestBody User user) {
    return userService.create(user, ROLE_USER);
  }

  /**
   * create an admin user
   *
   * @param user    user's role and his/her info
   * @return        new admin
   */
  @PostMapping("/sign-up/owner")
  public User creatUserAdmin(@Valid @RequestBody User user) {
    return userService.create(user, ROLE_OWNER);
  }

  /**
   * updating user's profile
   *
   * @param user    (to be updated)
   * @return        updated user
   */
  @PutMapping("/profile")
  public User update(@Valid @RequestBody User user) {
    return userService.updateByContext(user);
  }
}
