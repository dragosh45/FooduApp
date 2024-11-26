package com.tinderrestaurant.Tinder.Restaurants.controller;

import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.service.DishService;
import com.tinderrestaurant.Tinder.Restaurants.wrappers.DishIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * this class is to control the creation, deletion and modifying dishes
 */
@RestController
@RequestMapping("/dish")
public class DishController {
  private DishService dishService;

  @Autowired
  public DishController(DishService dishService) {
    this.dishService = dishService;
  }

  @GetMapping
  public List<Dish> getAllDishes() {
    return dishService.findAll();
  }

  /**
   * remove the addDishToCategory assigned to passed id
   *
   * @param id    of addDishToCategory
   */
  @DeleteMapping("/{id}")
  public void deleteDish(@PathVariable(value = "id") Long id) {
    this.dishService.delete(id);
  }

  /**
   * add the passed addDishToCategory to the list of dishes
   *
   * @param dish    new addDishToCategory
   * @return        saved addDishToCategory
   */
  @PostMapping
  public Dish newDish(@Valid @RequestBody Dish dish) {
    return dishService.save(dish);
  }

  /**
   * finding dishes using their ids
   *
   * @param ids   of dishes
   * @return      the list of all dishes with this ids.
   */
  @PostMapping("/getNewDish")
  public List<Dish> getNewDish(@RequestBody DishIds ids) {
    return dishService.getNewDish(ids);
  }

  /**
   * updating the addDishToCategory info
   *
   * @param id    new id (to be assigned to the addDishToCategory)
   * @param dish  the addDishToCategory (to be updated)
   */
  @PutMapping("/{id}")
  public void updateDish(@PathVariable(value = "id") Long id, @Valid @RequestBody Dish dish) {
    this.dishService.update(dish, id);
  }
}
