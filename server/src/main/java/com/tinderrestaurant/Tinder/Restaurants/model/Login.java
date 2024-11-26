package com.tinderrestaurant.Tinder.Restaurants.model;

import org.springframework.util.Assert;

import java.util.Objects;

/**
 * handling user's login
 * password   defining the entered password by the user
 */
public final class Login {

  private String password;

  public Login() {}

  public Login(String password) {
    Assert.notNull(password, "Password cannot be null");
    Assert.isTrue(password.length() >= 6, "Password should be bigger than 6 symbols");
    this.password = password;
  }

  //checking the equality of this password and the passed object
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Login login = (Login) o;
    return Objects.equals(password, login.password);
  }

  // getters and setters

  @Override
  public int hashCode() {
    return Objects.hash(password);
  }

  @Override
  public String toString() {
    return this.password;
  }

  //getter and setter for the password

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
