package com.tinderrestaurant.Tinder.Restaurants.service;

import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.wrappers.DishIds;

import java.util.List;

public interface DishService extends RepositoryService<Dish> {
    //returns the list of dishes after finding them in the database using their ids
    List<Dish> getNewDish(DishIds ids);
}
