package com.tinderrestaurant.Tinder.Restaurants.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.tinderrestaurant.Tinder.Restaurants.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    String header = req.getHeader(HEADER_STRING);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken auth = getAuthentication(req);

    SecurityContextHolder.getContext().setAuthentication(auth);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
    String token = req.getHeader(HEADER_STRING);

    if (token != null) {
      String user =
          JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
              .build()
              .verify(token.replace(TOKEN_PREFIX, ""))
              .getSubject();

      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
      }
      return null;
    }
    return null;
  }
}
