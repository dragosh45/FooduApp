package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  //searching for user in database using his/her name
  User findByEmail(String email);
}
