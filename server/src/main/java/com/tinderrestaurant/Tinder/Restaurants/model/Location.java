package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.Assert;

import javax.persistence.*;

/**
 * this class is to create a location table including its info
 * id         of location
 * latitude   defining the location's latitude from another location
 * longitude  defining the location's longitude from another location
 */
@Entity
@Table(name = "location")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private double latitude;

  @Column(nullable = false)
  private double longitude;

  //specifying the relation between a location and a user
  @JsonIgnore
  @OneToOne(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private User user;

  //specifying the relation between a location and a restaurant
  @JsonIgnore
  @OneToOne(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Restaurant restaurant;

  protected Location() {}

  public Location(double latitude, double longitude, User user) {
    Assert.notNull(user, "User must not be null");
    this.latitude = latitude;
    this.longitude = longitude;
    this.user = user;
  }

  // getters and setters

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }
}
