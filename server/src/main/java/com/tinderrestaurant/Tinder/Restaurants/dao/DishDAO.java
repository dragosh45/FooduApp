package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.exception.ResourceUnavailableException;
import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.model.Tag;
import com.tinderrestaurant.Tinder.Restaurants.repository.DishRepository;
import com.tinderrestaurant.Tinder.Restaurants.repository.TagRepository;
import com.tinderrestaurant.Tinder.Restaurants.service.DishService;
import com.tinderrestaurant.Tinder.Restaurants.wrappers.DishIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** retrieving/storing dishes from/to database */
@Service
public class DishDAO implements DishService {
  private final DishRepository dishRepository;
  private final TagRepository tagRepository;

  @Autowired
  public DishDAO(DishRepository dishRepository, TagRepository tagRepository) {
    this.dishRepository = dishRepository;
    this.tagRepository = tagRepository;
  }

  /**
   * finding list of dishes from database
   *
   * @return list of all dishes
   */
  @Override
  public List<Dish> findAll() {
    return dishRepository.findAll();
  }

  /**
   * saving the addDishToCategory to database
   *
   * @param dish the addDishToCategory (to be saved)
   * @return saved addDishToCategory
   */
  @Override
  public Dish save(Dish dish) {
    if (dish.getTags() != null) dish.setTags(manageTags(dish.getTags()));
      return this.dishRepository.save(dish);
  }

  /**
   * TODO: add documentation
   *
   * @param tags
   */
  private List<Tag> manageTags(List<Tag> tags) {
    List<Tag> resultSet = new ArrayList<>();

    for (Tag tag : tags) {
      System.out.println(tag.getName());
      System.out.println("existsByName: " + tagRepository.existsByName(tag.getName()));

      if (!tagRepository.existsByName(tag.getName())) {
        tag = tagRepository.save(tag);
      } else {
        tag = tagRepository.findByName(tag.getName());
      }
      resultSet.add(tag);
    }

    return resultSet;
   }

  /** 
   * finding the addDishToCategory in database using its id
   *
   * @param id of addDishToCategory
   * @return found addDishToCategory with the provided id
   * @throws ResourceUnavailableException if the addDishToCategory was not found
   */
  @Override
  public Dish findById(Long id) {
    return dishRepository
        .findById(id)
        .orElseThrow(() -> new ResourceUnavailableException("Dish", "id", id));
  }

  /**
   * finding the addDishToCategory using its id and then delete it from the database
   *
   * @param id of addDishToCategory
   * @throws ResourceUnavailableException if the addDishToCategory was not found
   */
  @Override
  public void delete(Long id) {
    Dish dishToDelete =
        dishRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Dish", "id", id));
    dishRepository.deleteById(id);
  }

  /**
   * finding the addDishToCategory using its id and then replace the old addDishToCategory with the
   * new one
   *
   * @param newDish (to replace the old addDishToCategory)
   * @param id of old addDishToCategory (to be updated)
   * @return repository after saving the modification
   * @throws ResourceUnavailableException if the addDishToCategory was not found
   */
  @Override
  public Dish update(Dish newDish, Long id) {
    Dish found =
        dishRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Dish", "id", id));

    return dishRepository.save(replaceDish(found, newDish));
  }

  /**
   * replacing the old addDishToCategory with a new one by setting its id, name, tag and category to
   * the new addDishToCategory
   *
   * @param oldDish addDishToCategory (to be changed)
   * @param newDish addDishToCategory (to be updated with the old addDishToCategory's info)
   * @return new addDishToCategory after getting old addDishToCategory's info
   */
  private Dish replaceDish(Dish oldDish, Dish newDish) {
    newDish.setId(oldDish.getId());
    newDish.setName(oldDish.getName());
    newDish.setTags(oldDish.getTags());
    newDish.setCategory(oldDish.getCategory());
    return newDish;
  }

  // returns the

  /**
   * finds dishes after finding them in the database using their ids
   *
   * @param ids of dishes
   * @return list of dishes after with the provided ids
   */
  @Override
  public List<Dish> getNewDish(DishIds ids) {
    return dishRepository.findDishesByIdIsNotIn(ids.getIds());
  }
}
