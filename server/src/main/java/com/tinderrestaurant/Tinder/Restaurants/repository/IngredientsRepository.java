package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Dish, Long> {}
