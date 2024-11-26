package com.tinderrestaurant.Tinder.Restaurants.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tinderrestaurant.Tinder.Restaurants.model.User;
import com.tinderrestaurant.Tinder.Restaurants.model.security.JwtBody;
import com.tinderrestaurant.Tinder.Restaurants.repository.UserRepository;
import com.tinderrestaurant.Tinder.Restaurants.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.tinderrestaurant.Tinder.Restaurants.security.SecurityConstants.EXPIRATION_TIME;
import static com.tinderrestaurant.Tinder.Restaurants.security.SecurityConstants.SECRET;

@Component
public class UserContext {

  private static UserRepository userRepository = null;

  @Autowired
  public UserContext(UserRepository userRepository) {
    UserContext.userRepository = userRepository;
  }

  //return user after finding her/him from database by her/his email
  public static User getUserFromContext() {
    return findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  public static User findByEmail(String userEmail) {
    return UserContext.userRepository.findByEmail(userEmail);
  }

  //authenticating token to allow users to sign information
  public static JwtBody generateJWT(User user) {
    Date expiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

    String token =
        JWT.create()
            .withSubject(user.getEmail())
            .withExpiresAt(expiresAt)
            .sign(Algorithm.HMAC512(SECRET.getBytes()));

    return new JwtBody(SecurityConstants.TOKEN_PREFIX + token, expiresAt, user.getRole());
  }
}
