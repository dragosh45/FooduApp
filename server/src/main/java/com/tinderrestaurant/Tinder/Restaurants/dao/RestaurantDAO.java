package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.exception.NoPermissionForResourceException;
import com.tinderrestaurant.Tinder.Restaurants.exception.ResourceUnavailableException;
import com.tinderrestaurant.Tinder.Restaurants.model.*;
import com.tinderrestaurant.Tinder.Restaurants.repository.*;
import com.tinderrestaurant.Tinder.Restaurants.service.RestaurantService;
import com.tinderrestaurant.Tinder.Restaurants.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tinderrestaurant.Tinder.Restaurants.security.SecurityConstants.ROLE_USER;

/**
 * retrieving/storing restaurants from/to database
 */
@Service
public class RestaurantDAO implements RestaurantService {

  private final RestaurantRepository restaurantRepository;

  private final UserRepository userRepository;

  private final OrderRepository orderRepository;

  private final DishRepository dishRepository;

  private final CategoryRepository categoryRepository;

  private final ReviewRepository reviewRepository;


  @Autowired
  public RestaurantDAO(
          RestaurantRepository restaurantRepository,
          UserRepository userRepository,
          OrderRepository orderRepository,
          DishRepository dishRepository,
          CategoryRepository categoryRepository, ReviewRepository reviewRepository) {
    this.restaurantRepository = restaurantRepository;
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
    this.dishRepository = dishRepository;
    this.categoryRepository = categoryRepository;
    this.reviewRepository = reviewRepository;
  }

  /**
   * finds all restaurant in the database
   *
   * @return  the list of restaurants
   */
  @Override
  public List<Restaurant> findAll() {
    return restaurantRepository.findAll();
  }

  /**
   * getting the unverified restaurants
   *
   * @return    list of the unverified restaurants
   */
  @Override
  public List<Restaurant> getUnverified() { return restaurantRepository.findRestaurantsByVerifiedFalse(); }


  /**
   * finding the restaurant using its is
   *
   * @param id    of restaurant
   * @return      found restaurant with the provided id
   * @throws      ResourceUnavailableException if the restaurant was not found
   */
  @Override
  public Restaurant findById(Long id) {
    return restaurantRepository
        .findById(id)
        .orElseThrow(() -> new ResourceUnavailableException("Restaurant", "id", id));
  }

    /**
     * finds the owner by email and then if the role indicated to owner
     * the restaurant will be saved to database and set the owner to that restaurant
     *
     * @param restaurant    restaurant (to be saved)
     * @return              saved restaurant
     * @throws               NoPermissionForResourceException if the restaurant was not authorised to do this task
     */
  @Override
  public Restaurant save(Restaurant restaurant) {
    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User owner = userRepository.findByEmail(userEmail);
    if (ROLE_USER.equals(owner.getRole().toString())) {
      throw new NoPermissionForResourceException("Restaurant", "any", "create");
    }
    restaurant.setOwner(owner);
    return restaurantRepository.save(restaurant);
  }

    /**
     * delete the restaurant fro the database after finding it using its id,
     * and whether the user have the permission to delete or not
     *
     * @param id    of restaurant
     */
  @Override
  public void delete(Long id) {
    Restaurant restaurant = findResource(id);
    checkPermission(restaurant.getOwner().getEmail());
    restaurantRepository.deleteById(id);
  }

    /**
     * updating the restaurant by replacing the old one with the new one
     * and checking if the user is authorised to update and then save it to database
     *
     * @param restaurant    new restaurant
     * @param id            of old restaurant
     * @return              saved updated restaurant
     */
  @Override
  public Restaurant update(Restaurant restaurant, Long id) {
    Restaurant oldRestaurant = findResource(id);
    checkPermission(oldRestaurant.getOwner().getEmail());

    return restaurantRepository.save(updateRestaurant(oldRestaurant, restaurant));
  }

  private Restaurant findResource(Long id) {
    return restaurantRepository
        .findById(id)
        .orElseThrow(() -> new ResourceUnavailableException("Restaurant", "id", id));
  }

    /**
     * retrieve the user email from database
     * and check if user email is equal to the passed email
     *
     * @param email   email (to be checked)
     * @throws        NoPermissionForResourceException if the restaurant is not authorised to do this task
     */
  private void checkPermission(String email) {
    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    Role userRole = userRepository.findByEmail(userEmail).getRole();
    if (!email.equals(userEmail) && !userRole.getName().equals("ADMIN")) {
      throw new NoPermissionForResourceException("Restaurant", "email", userEmail);
    }
  }

    /**
     * updating old restaurant's id and its owner and assign it to the new restaurant
     *
     * @param oldRes    old restaurant (to be updated)
     * @param newRes    new restaurant (to get oldRes info)
     * @return          updated new restaurant
     */
  private Restaurant updateRestaurant(Restaurant oldRes, Restaurant newRes) {
    newRes.setId(oldRes.getId());
    newRes.setOwner(oldRes.getOwner());
    return newRes;
  }

    /**
     * setting the user and the restaurant that was ordered from
     * and then save the order to database
     *
     * @param o               order (to be saved)
     * @param restaurantId    of restaurant
     * @return                saved order
     */
  @Override
  public Order order(Order o, Long restaurantId) {
    User user = UserContext.getUserFromContext();
    o.setRestaurant(findResource(restaurantId));
    o.setUser(user);
    return orderRepository.save(o);
  }

    /**
     * getting the list of orders from the restaurant and check its status
     *
     * @param restaurantId    id of restaurant
     * @param filter          defining the order status either processed or not
     * @return                status of the list of orders whether processed or not, otherwise returns all orders
     */
  @Override
  public List<Order> getOrders(Long restaurantId, String filter) {
    switch (filter) {
      case "processed":
        return orderRepository.findOrderByProcessedTrueAndRejectedFalse();
      case "not_processed":
        return orderRepository.findOrderByProcessedFalseAndRejectedFalse();
      default:
        return orderRepository.findAll();
    }
  }

  //TODO: remove redundant

  /**
   * saving a addDishToCategory to a restaurant
   *
   * @param d               addDishToCategory (to be saved)
   * @param restaurantId    id of restaurant (to save the addDishToCategory to)
   * @return                saved addDishToCategory
   */
  @Override
  public Dish dish(Dish d, Long restaurantId) {
//    d.(findResource(restaurantId));
    return dishRepository.save(d);
  }

    /**
     * enabling the restaurant to add its own categories
     * and create category list with all the restaurant's categories
     *
     * @param category    to be added
     * @param id          of restaurant
     * @return            saved restaurant with the provided category
     * @throws            ResourceUnavailableException if the restaurant is not authorised to do this task
     */
  @Override
  public Restaurant addCategory(Category category, Long id) {
    Restaurant res =
        this.restaurantRepository
            .findById(id)
            .orElseThrow(() -> new ResourceUnavailableException("Restaurant", "id", id));

    category.setRestaurant(res);
    category = categoryRepository.save(category);
    List<Category> categoryList = res.getCategories();
    categoryList.add(category);
    return restaurantRepository.save(res);
  }

  /**
   * getting the list of categories from the restaurant
   *
   * @param id    id of restaurant
   * @return      all the restaurant's categories
   */

  @Override
  public List<Category> getCategoriesFromRestaurant(Long id)
  {
    Restaurant res =
            this.restaurantRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceUnavailableException("Restaurant", "id", id));
    List<Category> categoryList = res.getCategories();
    return categoryList;
  }

  /**
   * enabling the restaurant to add the reviews made to the restaurant
   * by creating review list with all the restaurant's reviews
   *
   * @param review    to be added
   * @param id          of restaurant
   * @return            saved restaurant with the provided review
   * @throws            ResourceUnavailableException if the restaurant is not found
   */

  @Override
  public Restaurant addReview(Review review, Long id) {
    User user = UserContext.getUserFromContext();
    Restaurant res =
            this.restaurantRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceUnavailableException("Restaurant", "id", id));
    review.setRestaurant(res);
    review = reviewRepository.save(review);
    List<Review> reviews = res.getReviews();
    reviews.add(review);
    review.setUser(user);
    return restaurantRepository.save(res);
  }

  /**
   * getting the list of reviews from the restaurant
   *
   * @param id    id of restaurant
   * @return      all the restaurant's reviews
   */

  @Override
  public List<Review> getRestaurantReview(Long id) {
    Restaurant res =
            this.restaurantRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceUnavailableException("Restaurant", "id", id));
    List<Review> reviews = res.getReviews();
    return reviews;
  }
}
