package com.tinderrestaurant.Tinder.Restaurants.controller;

import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.model.Tag;
import com.tinderrestaurant.Tinder.Restaurants.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * this class is to control creation, deletion and modifying tags
 */
@RestController
@RequestMapping("/tag")
public class TagController {

  private final TagService tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  /**
   * saving new tag to the database
   *
   * @param tag   (to be added)
   * @return      saved tag
   */
  @PostMapping
  public Tag newTag(@Valid @RequestBody Tag tag) {
    return tagService.save(tag);
  }

  /**
   * finding all saved tags
   *
   * @return   list of tags
   */
  @GetMapping
  public List<Tag> findAllTags() {
    return tagService.findAll();
  }

  /**
   * update tag's info
   *
   * @param id      of tag
   * @param tag    (to be updated)
   * @return        updated tag
   */
  @PostMapping("/{id}")
  public Tag update(@PathVariable Long id, @RequestBody Tag tag) {
    return tagService.update(tag, id);
  }

  /**
   * finding tag using its id
   *
   * @param id    of tag
   * @return      tag with the specified id
   */
  @GetMapping("/{id}")
  public Tag findTagById(@PathVariable Long id) {
    return tagService.findById(id);
  }
  @GetMapping("/{id}/dishes")
  public List<Dish> getDishesFromTag(@PathVariable Long id)
  {
    return tagService.getDishes(id);
  }

  /**
   * finding tag using its name
   *
   * @param name    of tag
   * @return      tag with the specified name
   */
  @GetMapping("/{name}")
  public Tag findTagByName(@PathVariable String name) {
    return tagService.findByName(name);
  }

  /**
   * finding dishes using it tag name
   *
   * @param name    of tag
   * @return      dishes with the specified name
   */
  @GetMapping("/name/{name}/dishes")
  public List<Dish> findDishes(@PathVariable String name) {
    return tagService.getDishesByTagName(name);
  }

  /**
   * Create a addDishToCategory to tag with the given id
   *
   * @param id    of tag
   * @param dish  addDishToCategory (to be saved)
   * @return      JSON order
   */
  @PostMapping("/{id}/dish")
  public Dish dishToTag(@PathVariable(value = "id") Long id, @Valid @RequestBody Dish dish) {
    Tag tag = findTagById(id);
    tag.addDishes(dish);
    return tagService.dish(dish, id);
  }
  // TODO create addDishToCategory to tag by the tag name
}
