package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.exception.ResourceUnavailableException;
import com.tinderrestaurant.Tinder.Restaurants.model.Category;
import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.repository.CategoryRepository;
import com.tinderrestaurant.Tinder.Restaurants.repository.DishRepository;
import com.tinderrestaurant.Tinder.Restaurants.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * retrieving/storing categories from/to database
 */
@Service
public class CategoryDAO implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final DishRepository dishRepository;


  @Autowired
  public CategoryDAO(CategoryRepository categoryRepository, DishRepository dishRepository) {
    this.categoryRepository = categoryRepository;
    this.dishRepository = dishRepository;
  }

  /**
   * finding all categories
   *
   * @return    the list of categories from the database
   */
  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  /**
   * finding the category in database using its id
   *
   * @param id    of category
   * @return      category found with the provided id
   * @throws      ResourceUnavailableException if the category was not found
   */
  @Override
  public Category findById(Long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceUnavailableException("Category", "id", id));
  }

  /**
   * saving the category to database
   *
   * @param category    (to be saved)
   * @return            saved category
   */
  @Override
  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  /**
   * removing category from database using its id
   *
   * @param id    of category
   */
  @Override
  public void delete(Long id) {
    categoryRepository.deleteById(id);
  }

  /**
   * updating category's id, by searching if the category exists first then set
   * the category to the new id
   *
   * @param entity    category to be updated
   * @param id        new id to be assigned to the category
   * @return          saved category in database after being updated
   */
  @Override
  public Category update(Category entity, Long id) {
    Category existing = findById(id);
    if (categoryRepository.existsById(id)) {
      entity.setId(existing.getId());
    }
    return save(entity);
  }

  /**
   * finding the resource of a category using its id
   *
   * @param id    of category
   * @return      repository after finding the category
   * @throws      ResourceUnavailableException if the category was not found
   */
  private Category findResource(Long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceUnavailableException("Category", "id", id));
  }

  /**
   * updating an old category with new name,
   * and assigning old category's dishes to the new one
   *
   * @param oldCat    (to be updated)
   * @param newCat    the new category (to be assigned to old one)
   * @return          new category after the modification
   */
  private Category updateCategory(Category oldCat, Category newCat) {
    newCat.setId(oldCat.getId());
    newCat.setName(oldCat.getName());
    newCat.setDishes(oldCat.getDishes());
    return newCat;
  }

  /**
   * assign the addDishToCategory to a category using the category's id
   * by finding its resource, and save it
   *
   * @param d             addDishToCategory (to be assigned)
   * @param categoryId    of category
   * @return              saved addDishToCategory with the provided category
   */
  @Override
  public Dish dish(Dish d, Long categoryId) {
    d.setCategory(findResource(categoryId));
    return dishRepository.save(d);
  }

  /**
   * finds the list of dishes using their ids
   *
   * @param id    of dishes
   * @return      list of dishes with the provided ids
   * @throws      ResourceUnavailableException if the category id was not found
   */
  public List<Dish> getDishes(Long id)
  {
    Category category = categoryRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Category", "id", id));
    return category.getDishes();
  }

  /**
   * searching for the category using its name in the database
   *
   * @param name    of category
   * @return        found category with the provided name
   * @throws        ResourceUnavailableException if the category was not found
   */
  @Override
  public Category findByName(String name) {
    if (categoryRepository.findByName(name) == null)
      throw new ResourceUnavailableException("Category", "name", name);
    return categoryRepository.findByName(name);
  }
}
