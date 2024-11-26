package com.tinderrestaurant.Tinder.Restaurants.wrappers;

import java.util.List;

public class DishIds {
  public DishIds() {}

    //returns list of ids from database
    List<Long> ids;

    //getter and setter for ids
    public List<Long> getIds() {
        return ids;
    }

  public void setIds(List<Long> ids) {
    this.ids = ids;
  }
}
