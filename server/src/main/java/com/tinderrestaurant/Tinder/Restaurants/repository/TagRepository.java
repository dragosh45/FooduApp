package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  /**
   * Search for a tag in database using its name
   * @param name of the tag
   * @return database entity mapped to a Tag
   */
  Tag findByName(String name);

  /**
   * Check if a tag exists in the db
   * @param name of the tag
   * @return
   */
  boolean existsByName(String name);
}
