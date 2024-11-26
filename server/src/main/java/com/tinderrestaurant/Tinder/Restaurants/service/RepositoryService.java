package com.tinderrestaurant.Tinder.Restaurants.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RepositoryService<T> {

  //finding elements from database
  List<T> findAll();

  //finding item by its id
  T findById(Long id);

  //saving item to database
  T save(T entity);

  //delete item from database using its id
  void delete(Long id);

  //updating entity info using its id
  T update(T entity, Long id);
}
