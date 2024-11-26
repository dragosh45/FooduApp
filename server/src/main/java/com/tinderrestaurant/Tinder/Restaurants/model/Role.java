package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * this class is to create a role table including its info,
 * id     of role
 * name   of role
 */
@Entity
@Table(name = "role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  //specifying the relation between role and users
  @JsonIgnore
  @OneToMany(targetEntity = User.class, mappedBy = "role", cascade = CascadeType.ALL)
  private List<User> users;

  public Role() {}

  public Role(String name) {
    this.name = name;
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

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return name;
  }
}
