package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.exception.ResourceUnavailableException;
import com.tinderrestaurant.Tinder.Restaurants.model.Category;
import com.tinderrestaurant.Tinder.Restaurants.model.Dish;
import com.tinderrestaurant.Tinder.Restaurants.model.Tag;
import com.tinderrestaurant.Tinder.Restaurants.repository.DishRepository;
import com.tinderrestaurant.Tinder.Restaurants.repository.TagRepository;
import com.tinderrestaurant.Tinder.Restaurants.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * retrieving/storing tags from/to database
 */
@Service
public class TagDAO implements TagService {
  private final TagRepository tagRepository;
  private final DishRepository dishRepository;

  @Autowired
  public TagDAO(TagRepository tagRepository, DishRepository dishRepository) {
    this.tagRepository = tagRepository;
    this.dishRepository = dishRepository;
  }

  //returns list of all the tags in database
  @Override
  public List<Tag> findAll() {
    return tagRepository.findAll();
  }

  /**
   * checking if the tag exists using its id and then update that id with new tag
   * @param tag - (to be updated)
   * @param id - of old tag
   * @return - saved tag in database
   */
  @Override
  public Tag update(Tag tag, Long id) {
    Tag existing = findById(id);
    if (tagRepository.existsById(id)) {
      tag.setId(existing.getId());
    }
    return save(tag);
  }

  //returns repository after saving new tag to database
  @Override
  public Tag save(Tag tag) {
    return tagRepository.save(tag);
  }

  /**
   * finding tag using its id
   *
   * @param id    of tag (to be found)
   * @return      found tag with the provided id
   * @throws      ResourceUnavailableException if the tag was not found
   */
  public Tag findById(Long id) {
    return tagRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Tag", "id", id));
  }

  /**
   * searching for tag in database using its name
   *
   * @param name  of tag
   * @return      found tag with the provided name
   */
  @Override
  public Tag findByName(String name) {
      if (tagRepository.findByName(name) == null)
        throw new ResourceUnavailableException("Tag", "name", name);
    return tagRepository.findByName(name);
  }

  /**
   * finding list of dishes by tag name
   *
   * @param name  of tag
   * @return      list of dishes of given tag
   */
  @Override
  public List<Dish> getDishesByTagName(String name) {
    Tag tag = findByName(name);
    //if (tagRepository.existsByName(tag.getName())) {
    if (tag == null){
      throw new ResourceUnavailableException("Tag", "name", name);
    }
    return tag.getDishes();
  }
  //

  /**
   * checking if a tag exists using its id, and then removing it from database
   *
   * @param id    of tag
   */
  @Override
  public void delete(Long id) {
    Tag tag = findResource(id);
    tagRepository.deleteById(id);
  }

  /**
   *finding tag using its id
   *
   * @param id    of tag
   * @return      found tag
   * @throws      ResourceUnavailableException if the tag was not found
   */
  private Tag findResource(Long id) {
    return tagRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Tag", "id", id));
  }

  /**
   * getting the old tag id, name and related dishes and set it to the new tag
   *
   * @param oldTag    tag (to set its info to the newTag)
   * @param newTag    tag (to be updated)
   * @return          new tag with all old tag info
   */
  private Tag updateTag(Tag oldTag, Tag newTag) {
    newTag.setId(oldTag.getId());
    newTag.setName(oldTag.getName());
    newTag.setDishes(oldTag.getDishes());
    return newTag;
  }

  /**
   * creating a list of dishes that are related to one tag id,
   * then save the addDishToCategory
   *
   * @param d       addDishToCategory (to be saved)
   * @param tagId   id of tag
   * @return        saved tag if it is found, else null
   */
  @Override
  public Dish dish(Dish d, Long tagId) {
    List<Dish> dishList = findById(tagId).getDishes();
    if (dishList.contains(d)) {
      return dishRepository.save(d);
    }
    return null; // dishList from the specified Tag with tagId must have the current addDishToCategory(d)
  }
  public List<Dish> getDishes(Long id)
  {
    Tag tag = tagRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Tag", "id", id));
    return tag.getDishes();
  }
}
