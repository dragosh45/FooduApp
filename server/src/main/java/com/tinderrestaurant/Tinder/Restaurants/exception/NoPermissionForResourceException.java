package com.tinderrestaurant.Tinder.Restaurants.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * creating a costume exception to check whether the user is authorised for a certain task
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoPermissionForResourceException extends RuntimeException {

  private String resourceName;
  private String fieldName;
  private Object fieldValue;

  public NoPermissionForResourceException(
      String resourceName, String fieldName, Object fieldValue) {
    super(
        String.format(
            "You do not have permissions for %s with %s : %s",
            resourceName, fieldName, fieldValue));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public String getResourceName() {
    return resourceName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }
}
