package com.tinderrestaurant.Tinder.Restaurants.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * creating a costume exception to check the availability of an item in the database
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceUnavailableException extends RuntimeException {

  private String resourceName;
  private String fieldName;
  private Object fieldValue;

  public ResourceUnavailableException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
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
