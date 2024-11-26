package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * this class is to create a category table including its info,
 * id     of category
 * name   of category
 */
@Entity
@Table(name = "category")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  //specifying the relation of the categories with dishes
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
  /*@JoinTable(name = "categories_dishes",
  joinColumns = { @JoinColumn(name = "categories_id") },
  inverseJoinColumns = { @JoinColumn(name = "dishes_id") })*/
  //defining the list of related dishes
  private List<Dish> dishes;

  //specifying the relation of the categories with restaurants
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  public Category() {}

  public Category(String name) {
    this.name = name;
  }

  // getters and setters

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
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

  //returns category's info
  @Override
  public String toString() {
    return "Category{" + "id=" + id + ", name='" + name + '\'' + ", dishes=" + dishes + '}';
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }
}
