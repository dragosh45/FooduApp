package com.tinderrestaurant.Tinder.Restaurants.controller;

import com.tinderrestaurant.Tinder.Restaurants.model.Category;
import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.service.CategoryService;
import com.tinderrestaurant.Tinder.Restaurants.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/** this class is to control the creation, deletion and finding categories */
@RestController
@RequestMapping("/category")
public class CategoryController {

  private final CategoryService categoryService;
  private final DishService dishService;

  @Autowired
  public CategoryController(CategoryService categoryService, DishService dishService) {
    this.categoryService = categoryService;
    this.dishService = dishService;
  }

  /**
   * Create addDishToCategory to category with the given id
   *
   * @param id of category
   * @param dish addDishToCategory (to be saved)
   * @return JSON order
   */
  @PostMapping("/{id}/dish")
  public Dish addDishToCategory(
      @PathVariable(value = "id") Long id, @Valid @RequestBody Dish dish) {

    dish.setCategory(categoryService.findById(id));
    return dishService.save(dish);
  }
  // TODO create addDishToCategory to category with the given category name

  /**
   * getting all dishes related to a dishes
   *
   * @param id of category
   * @return list of dishes with the provided category id
   */
  @GetMapping("/{id}/dishes")
  public List<Dish> getDishesFromCategory(@PathVariable Long id) {
    return categoryService.getDishes(id);
  }

  /**
   * finds all the categories
   *
   * @return list of categories found in database
   */
  @GetMapping("/categories")
  public List<Category> getAllCategories() {
    return categoryService.findAll();
  }


  /**
   * finds the category by id
   *
   * @param id of category
   * @return category found with given id
   */
  @GetMapping("/{id}")
  public Category findCategoryById(@PathVariable Long id) {
    return categoryService.findById(id);
  }

  /**
   * delete category with the specified id
   *
   * @param id of category
   */
  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable Long id) {
    categoryService.delete(id);
  }
}
