package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.exception.ResourceUnavailableException;
import com.tinderrestaurant.Tinder.Restaurants.model.*;
import com.tinderrestaurant.Tinder.Restaurants.model.security.JwtBody;
import com.tinderrestaurant.Tinder.Restaurants.model.security.SecurityUser;
import com.tinderrestaurant.Tinder.Restaurants.repository.LocationRepository;
import com.tinderrestaurant.Tinder.Restaurants.repository.RoleRepository;
import com.tinderrestaurant.Tinder.Restaurants.repository.UserRepository;
import com.tinderrestaurant.Tinder.Restaurants.service.UserService;
import com.tinderrestaurant.Tinder.Restaurants.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * retrieving/storing users from/to database
 */
@Service
public class UserDAO implements UserService, UserDetailsService {

  private final UserRepository userRepository;

  private final LocationRepository locationRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  private final RoleRepository roleRepository;

  // Leaving field injection since dependency cycle being formed
  @Autowired private AuthenticationManager authenticationManager;

  @Autowired
  public UserDAO(
      UserRepository userRepository,
      LocationRepository locationRepository,
      BCryptPasswordEncoder passwordEncoder,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.locationRepository = locationRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  //  @Autowired
  //  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
  //    this.authenticationManager = authenticationManager;
  //  }

  //returns list of users from database

  /**
   * finds all users in the database
   *
   * @return   list of users
   */
  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  /**
   * finds user using its id
   *
   * @param id  of user
   * @return    found user with the provided id
   */
  @Override
  public User findById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceUnavailableException("User", "id", id));
  }

  //saving new uer to database

  /**
   * saving a new user to the database
   *
   * @param entity  user (to be saved)
   * @return        saved user
   */
  @Override
  public User save(User entity) {
    return userRepository.save(entity);
  }

  /**
   * setting the role of a user and his/her login details, and save them
   *
   * @param user    (to be created)
   * @param role    of user
   * @return        saved user
   */
  @Override
  public User create(User user, final String role) {
    // TODO: add salt
    user.setRole(roleRepository.findByName(role));
    user.setLogin(new Login(passwordEncoder.encode(user.getLogin().toString())));
    return userRepository.save(user);
  }

  /**
   * adding location to user and save it to database
   *
   * @param location   of user
   * @return           user with added location to her/his info
   */
  @Override
  public User addLocation(Location location) {
    User currentlyLoggedOn = getUserFromContext();
    location.setUser(currentlyLoggedOn);
    currentlyLoggedOn.setLocation(location);
    locationRepository.save(location);
    return currentlyLoggedOn;
  }

  //returns user after updating his/her context and saving it to database

  /**
   * updating user info and store it to newUser
   *
   * @param user    (to be updated)
   * @return        saved new user with the new update
   */
  @Override
  public User updateByContext(User user) {
    User newUser = updateUser(getUserFromContext(), user);
    return userRepository.save(newUser);
  }

  /**
   * getting the orders for the user
   *
   * @return    list of orders to that user
   */
  @Override
  public List<Order> getOrders() {
    return UserContext.getUserFromContext().getOrders();
  }

  //returns repository if the user's email is authenticated

  /**
   * finding the user using its email
   *
   * @return    user authentication status
   */
  @Override
  public User findAuthenticated() {
    return userRepository.findByEmail(UserContext.getUserFromContext().getEmail());
  }

  /**
   * authenticating users
   *
   * @param user  user's authentication info
   * @return      authentication status
   */
  @Override
  public JwtBody authenticateUser(SecurityUser user) {
    final Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    final User fetchedUser = userRepository.findByEmail(user.getUsername());
    return UserContext.generateJWT(fetchedUser);
  }

  /**
   * getting the list of reviews from the user
   *
   * @return      all the users's reviews
   */

  @Override
  public List<Review> getReviews() {
    final User fetchedUser = userRepository.findByEmail(UserContext.getUserFromContext().getEmail());
    return fetchedUser.getReviews();
  }

  /**
   * setting old user's info e.g. id, role, login. location and email to the new user
   *
   * @param oldUser   the user to get her/his info
   * @param newUser   user (to be updated)
   * @return          updated user
   */
  private User updateUser(User oldUser, User newUser) {
    newUser.setId(oldUser.getId());
    newUser.setRole(oldUser.getRole());
    newUser.setLogin(oldUser.getLogin());
    newUser.setLocation(oldUser.getLocation());
    newUser.setEmail(oldUser.getEmail());
    return newUser;
  }

  /**
   * finding user using his/her id then remove it from database
   *
   * @param id    of user
   * @throws      ResourceUnavailableException if the user was not found
   */
  @Override
  public void delete(Long id) {
    userRepository.delete(
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("User", "Id", id)));
  }

  /**
   * updating the user with new id
   *
   * @param entity  user (to be updated)
   * @param id      of user
   * @return        updated user
   */
  @Override
  public User update(User entity, Long id) {
    entity.setId(id);
    return updateByContext(entity);
  }

  //returns user's saved info form database

  /**
   * getting the user's info
   *
   * @return    the profile of a user
   */
  @Override
  public User getProfile() {
    return getUserFromContext();
  }

  //returns user found by resource

  /**
   * creating new user to get the email and find the user's info using her/his email
   *
   * @return    found user by her/his email
   */
  private User getUserFromContext() {
    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    return findResource(userEmail);
  }

  //return user found by email

  /**
   * finds the user from database using her/his email
   *
   * @param userEmail   the user email (to be searched)
   * @return            found user with the provided email
   */
  private User findResource(String userEmail) {
    return userRepository.findByEmail(userEmail);
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * finds user by his/hers email
   *
   * @param email   personal mail
   * @return        found user with the provided email
   * @throws        UsernameNotFoundException if user with this mail is not found
   * @throws        ResourceUnavailableException if the user was not found
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User found = userRepository.findByEmail(email);
    if (found == null) {
      throw new ResourceUnavailableException("User", "email", email);
    }
    return new org.springframework.security.core.userdetails.User(
        found.getEmail(), found.getLogin().toString(), Collections.emptyList());
  }
}
