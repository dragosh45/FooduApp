package com.tinderrestaurant.Tinder.Restaurants.controller;

import com.tinderrestaurant.Tinder.Restaurants.model.security.JwtBody;
import com.tinderrestaurant.Tinder.Restaurants.model.security.SecurityUser;
import com.tinderrestaurant.Tinder.Restaurants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * this class is to control user login to the app
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

  private final UserService userService;

  @Autowired
  public LoginController(UserService userService) {
    this.userService = userService;
  }

  /**
   * activate the user authentication
   *
   * @param user    user role
   * @return        authentication object
   */
  @PostMapping
  public JwtBody authenticateUser(@Valid @RequestBody SecurityUser user) {
    return userService.authenticateUser(user);
  }
}