package com.tinderrestaurant.Tinder.Restaurants.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LoginAttributeConverter implements AttributeConverter<Login, String> {

  //converting the login parameter to String
  @Override
  public String convertToDatabaseColumn(Login attribute) {
    return attribute.toString();
  }

  //converting the String parameter to a Login object
  @Override
  public Login convertToEntityAttribute(String dbData) {
    return new Login(dbData);
  }
}
