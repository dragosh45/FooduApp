package com.tinderrestaurant.Tinder.Restaurants.service;

import com.tinderrestaurant.Tinder.Restaurants.model.Category;
import com.tinderrestaurant.Tinder.Restaurants.model.Dish;

import java.util.List;

public interface CategoryService extends RepositoryService<Category> {
  Dish dish(Dish d, Long CategoryId);

  //getting all dishes related to the category id
  List<Dish> getDishes(Long id);

  //searching for the category in the database
  Category findByName(String name);
}
