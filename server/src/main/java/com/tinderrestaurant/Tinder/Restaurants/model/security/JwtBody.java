package com.tinderrestaurant.Tinder.Restaurants.model.security;

import com.tinderrestaurant.Tinder.Restaurants.model.Role;

import java.util.Date;

public class JwtBody {
  private String token;
  private Date expiresAt;
  private Role role;

  JwtBody() {}

  public JwtBody(String token, Date expiresAt, Role role) {
    this.token = token;
    this.expiresAt = expiresAt;
    this.role = role;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  public Role getRole() { return role; }

  public void setRole(Role role) { this.role = role; }
}
