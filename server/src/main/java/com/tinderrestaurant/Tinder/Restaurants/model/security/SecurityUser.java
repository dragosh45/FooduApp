package com.tinderrestaurant.Tinder.Restaurants.model.security;

import com.tinderrestaurant.Tinder.Restaurants.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

  private String email;
  private String password;
  private Role role;
  private boolean isBanned;
  private boolean isVerified;

  /**
   * Creates template POJO for user security purposes
   *
   * @param email
   * @param password
   * @param role
   * @param blocked
   * @param verified
   */
  public SecurityUser(String email, String password, Role role, boolean blocked, boolean verified) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.isBanned = blocked;
    this.isVerified = verified;
  }

  public SecurityUser() {}

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> roles = new ArrayList<>();
    roles.add(new SimpleGrantedAuthority(role.toString()));
    return roles;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public void setBanned(boolean banned) {
    isBanned = banned;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean verified) {
    isVerified = verified;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isVerified;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !isBanned;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !isBanned;
  }

  @Override
  public boolean isEnabled() {
    return isVerified;
  }

  @Override
  public String toString() {
    return "SecurityUser{"
        + "email='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + ", role="
        + role
        + ", isBanned="
        + isBanned
        + ", isVerified="
        + isVerified
        + '}';
  }
}
