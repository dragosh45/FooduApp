package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * this class is to create ingredients table including its info
 * id     of ingredient
 * name   of ingredient
 */
@Entity
@Table(name = "ingredient")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ingredients {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  //specifying the relation between the ingredient and the dishes
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "dish_id")
  //defining a related addDishToCategory
  private Dish dish;

  public Ingredients() {}

  // getters and setters

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Dish getDish() {
    return dish;
  }

  public void setDish(Dish dish) {
    this.dish = dish;
  }

  //returns ingredients's info
  @Override
  public String toString() {
    return "Ingredients{" + "id=" + id + ", name='" + name + '\'' + ", dish=" + dish + '}';
  }
}
