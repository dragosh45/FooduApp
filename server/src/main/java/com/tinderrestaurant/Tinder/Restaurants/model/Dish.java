package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;

/**
 * this class is to create a addDishToCategory table including its info,
 * id              of dish
 * name            of dish
 * imageUrl        of dish
 * description     of dish
 * price           of dish
 * timeInMinutes   defining dish's preparation time
 */
@Entity
@Table(name = "dish")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Dish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column private String imageUrl;

  @Column private String description;

  @Column private float price;

  @Column private int timeInMinutes;

  //specifying the relation between the addDishToCategory and its ingredients
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "dish")
  private List<Ingredients> ingredients;

  //specifying the relation between the addDishToCategory and tags, and join the ids' columns
  //in both tables (addDishToCategory and tags)
  @ManyToMany(fetch = FetchType.LAZY, targetEntity = Tag.class, cascade = CascadeType.PERSIST)
//  @JoinTable(
//      name = "dishes_tags",
//      joinColumns = {@JoinColumn(name = "dishes_id")},
//      inverseJoinColumns = {@JoinColumn(name = "tags_id")})
//  //defining the list of the related tags
  private List<Tag> tags;

  //specifying the relation between the addDishToCategory and category
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Category category;

  //specifying the relation between the addDishToCategory and the order, and join the ids' columns
  //for both tables (addDishToCategory and order)
  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "dish_order",
      joinColumns = {@JoinColumn(name = "dish_id")},
      inverseJoinColumns = {@JoinColumn(name = "order_id")})
  //defining the list of orders that contains the addDishToCategory
  private List<Order> orders;

  public Dish() {}

  public Dish(String name, List<Tag> tags, Category category) {
    this.name = name;
    this.tags = tags;
    this.category = category;
  }

  public Dish(Long id, String name, Restaurant restaurant, Category category) {
    this.id = id;
    this.name = name;
    Assert.notNull(restaurant, "Restaurant must not be null");
    this.category = category;
  }

  public Dish(Long id, String name, Category category, String imageUrl) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.imageUrl = imageUrl;
  }

  // getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getTimeInMinutes() {
    return timeInMinutes;
  }

  public void setTimeInMinutes(int timeInMinutes) {
    this.timeInMinutes = timeInMinutes;
  }

  public List<Ingredients> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredients> ingredients) {
    this.ingredients = ingredients;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  //returns addDishToCategory's information
  @Override
  public String toString() {
    return "Dish{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", imageUrl='"
        + imageUrl
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", timeInMinutes="
        + timeInMinutes
        + ", ingredients="
        + ingredients
        + ", tags="
        + tags
        + ", category="
        + category
        + '}';
  }
}
