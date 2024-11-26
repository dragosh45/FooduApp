package com.tinderrestaurant.Tinder.Restaurants.security;

public class SecurityConstants {
  public static final String SECRET = "SeCrETFoRjWT1DX";
  public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24-hours
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/user/sign-up";

  public static final String ROLE_USER = "USER";
  public static final String ROLE_OWNER = "OWNER";
  public static final String ROLE_ADMIN = "ADMIN";
}
