package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * this class is to create a user table including its info,
 * id           of user
 * givenNames   of user
 * surname      of user
 * email        of user
 * photoUrl     of user
 * isBanned     defining whether the user is banned or not
 * isVerified   defining whether the user is verified
 * location     of user
 * login        defining user's login
 */
@Entity
@Table(name = "customer")
@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt", "hibernateLazyInitializer", "handler"},
    allowGetters = true)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "given_names", nullable = false)
  private String givenNames;

  @Column(nullable = false)
  private String surname;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(name = "photo_url")
  private String photoUrl;

  @Column(columnDefinition = "boolean default false", name = "banned")
  private boolean isBanned;

  @Column(columnDefinition = "boolean default true", name = "verified")
  private boolean isVerified;

  //specifying the relation between user and location
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  private Location location;

  @Column(nullable = false, name = "password")
  private Login login;

  //specifying the relation between users and role
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  //specifying the relation between user and restaurant
  @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
  private Restaurant restaurant;

  //specifying the relation between user and orders
  @JsonIgnore
  @OneToMany(targetEntity = Order.class, mappedBy = "user", cascade = CascadeType.ALL)
  private List<Order> orders;

  //specifying the relation between user and reviews
  @JsonIgnore
  @OneToMany(targetEntity = Review.class, mappedBy = "user", cascade = CascadeType.PERSIST)
  private List<Review> reviews;

  public User() {}

  private User(String givenNames, String surname, String email, Login password) {
    Assert.hasLength(givenNames, "Given Names must not be empty");
    Assert.hasLength(surname, "Surname must not be empty");
    Assert.hasLength(email, "Email must not be empty");
    Assert.notNull(password, "Password must not be null");
    this.givenNames = givenNames;
    this.surname = surname;
    this.email = email;
    this.login = password;
  }

  public User(
      String givenNames,
      String surname,
      String email,
      Login password,
      String photoUrl,
      boolean banned,
      boolean verified,
      Location location) {
    this(givenNames, surname, email, password);
    this.photoUrl = photoUrl;
    this.isBanned = banned;
    this.isVerified = verified;
    this.location = location;
  }

  //getters and setters

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGivenNames() {
    return givenNames;
  }

  public void setGivenNames(String givenNames) {
    this.givenNames = givenNames;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public void setBanned(boolean banned) {
    this.isBanned = banned;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean verified) {
    this.isVerified = verified;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Login getLogin() {
    return login;
  }

  public void setLogin(Login login) {
    this.login = login;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return isBanned == user.isBanned
        && isVerified == user.isVerified
        && Objects.equals(id, user.id)
        && Objects.equals(givenNames, user.givenNames)
        && Objects.equals(surname, user.surname)
        && Objects.equals(email, user.email)
        && Objects.equals(photoUrl, user.photoUrl)
        && Objects.equals(location, user.location)
        && Objects.equals(login, user.login)
        && Objects.equals(role, user.role)
        && Objects.equals(restaurant, user.restaurant);
  }

  //returns user's info
  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", givenNames='"
        + givenNames
        + '\''
        + ", surname='"
        + surname
        + '\''
        + ", email='"
        + email
        + '\''
        + ", photoUrl='"
        + photoUrl
        + '\''
        + ", isBanned="
        + isBanned
        + ", isVerified="
        + isVerified
        + ", location="
        + location
        + ", login="
        + login
        + ", role="
        + role
        + '}';
  }
}
