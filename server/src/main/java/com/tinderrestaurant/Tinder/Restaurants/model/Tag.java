package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * this class is to create a tag table including its info,
 * id     of tag
 * name   of tag
 */
@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  private String name;


  //specifying the relation between tags and dishes
  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "dish_tags",
      joinColumns = {@JoinColumn(name = "tags_id")},
      inverseJoinColumns = {@JoinColumn(name = "dish_id")})
  //defining related list of dishes
  private List<Dish> dishes;

  public Tag() {}

  public Tag(String tag) {
    name = tag;
  }

  //getters and setters

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Dish> getDishes() {
    return dishes;
  }

  public void setDishes(List<Dish> dishes) {
    this.dishes = dishes;
  }

  public void addDishes(Dish dish) {
     this.dishes.add(dish);
  }

  //returns tag's info
  @Override
  public String toString() {
    return "Tag{" + "id=" + id + ", name='" + name + '\'' + ", dishes=" + dishes + '}';
  }
}
