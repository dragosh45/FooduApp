package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * this class is to create a restaurant table including its info,
 * id         of restaurant
 * name       of restaurant
 * logoUrl    defining restaurant's logo Url
 * phone      of restaurant
 * isVerified defining whether the restaurant is verified or not
 * location   of restaurant
 */
@Entity
@Table(name = "restaurant")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column private String logoUrl;

  @Column private String phone;

  @Column(name = "verified", columnDefinition = "boolean default false")
  private boolean verified;

  //specifying the relation between restaurant and location
  @OneToOne
  @JoinColumn(name = "location_id")
  private Location location;

  //specifying the relation between restaurant and user
  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "customer_id", unique = true)
  private User owner;

  //specifying the relation between restaurant and orders
  @JsonIgnore
  @OneToMany(targetEntity = Order.class, mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Order> orders;

  //specifying the relation between restaurant and categories
  @OneToMany(targetEntity = Category.class, mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Category> categories;

  //specifying the relation between restaurant and reviews
  @OneToMany(targetEntity = Review.class, mappedBy = "restaurant", cascade = CascadeType.PERSIST)
  private List<Review> reviews;

  public Restaurant() {}

  public Restaurant(
      String name,
      String logoUrl,
      String phone,
      boolean verified,
      Location location,
      User owner) {
    this.name = name;
    this.logoUrl = logoUrl;
    this.phone = phone;
    this.verified = verified;
    this.location = location;
    this.owner = owner;
  }

  // getters and setters

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

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

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public boolean getVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public void addCategory(Category cat) {
    categories.add(cat);
  }

  public void deleteCategory(Category cat) {
    categories.remove(cat);
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public void addReview(Review review) {
    reviews.add(review);
  }
}
