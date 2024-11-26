package com.tinderrestaurant.Tinder.Restaurants.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {

  @GetMapping
  public String getGreeting() {
    return "<h1>Hello there! This is our app</h2>";
  }
}
