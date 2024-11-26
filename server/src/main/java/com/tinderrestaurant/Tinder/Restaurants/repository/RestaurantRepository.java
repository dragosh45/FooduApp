package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    //return list of the unverified restaurants
    List<Restaurant> findRestaurantsByVerifiedFalse();
}
