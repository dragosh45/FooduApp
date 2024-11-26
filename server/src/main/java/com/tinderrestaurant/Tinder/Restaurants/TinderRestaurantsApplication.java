package com.tinderrestaurant.Tinder.Restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(
    scanBasePackages = {
      "com.tinderrestaurant.Tinder.Restaurants.service",
      "com.tinderrestaurant.Tinder.Restaurants"
    })
public class TinderRestaurantsApplication {

  public static void main(String[] args) {
    SpringApplication.run(TinderRestaurantsApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
