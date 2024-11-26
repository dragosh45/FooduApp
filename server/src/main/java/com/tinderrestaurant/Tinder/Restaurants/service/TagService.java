package com.tinderrestaurant.Tinder.Restaurants.service;

import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.model.Tag;

import java.util.List;

public interface TagService extends RepositoryService<Tag> {
  //creating a list of dishes that are related to one tag's id,
  //then save the addDishToCategory to addDishToCategory repository
  Dish dish(Dish d, Long TagId);

  /**
   * finding tag using its name
   *
   * @param name    of tag
   * @return      tag with the specified name
   */
  Tag findByName(String name);

  /**
   * finding list of dishes by tag name
   *
   * @param name  of tag
   * @return      list of dishes of given tag
   */
  List<Dish> getDishesByTagName(String name);
  List<Dish> getDishes(Long id);
}
