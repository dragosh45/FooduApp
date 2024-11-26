package com.tinderrestaurant.Tinder.Restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * this class is to create an order table including its info,
 * id         of order
 * timestamp  defining the time of the order
 * seats      defining the number of seats required
 * tip        defining a tip for a restaurant
 * processed  defining whether the order was processed or not
 * serveTime  defining the required time for the order to be served
 */
@Entity
@Table(name = "order_entity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "time_of_order", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  @Column(columnDefinition = "SMALLINT default '1'")
  private int seats;

  @Column(columnDefinition = "SMALLINT default '0'")
  private int tip;

  @Column(columnDefinition = "boolean default false")
  private boolean processed;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date serveTime;

  //specifying the relation between orders and a user
  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id")
  private User user;

  //specifying the relation between orders and a restaurant
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  //specifying the relation between orders and dishes
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<Dish> dishes;

  @Column(columnDefinition = "boolean default false")
  private boolean paid;

  @Column(columnDefinition = "boolean default false")
  private boolean rejected;

  public Order() {}

  public Order(int seats, Date serveTime, User user, Restaurant restaurant) {
    Assert.notNull(serveTime, "Serve time must be provided");
    Assert.notNull(user, "User must not be null");
    Assert.notNull(restaurant, "Restaurant must not be null");
    this.seats = seats;
    this.serveTime = serveTime;
    this.user = user;
    this.restaurant = restaurant;
  }

  // getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public int getSeats() {
    return seats;
  }

  public void setSeats(int seats) {
    this.seats = seats;
  }

  public int getTip() {
    return tip;
  }

  public void setTip(int tip) {
    this.tip = tip;
  }

  public Date getServeTime() {
    return serveTime;
  }

  public void setServeTime(Date serveTime) {
    this.serveTime = serveTime;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return seats == order.seats
        && tip == order.tip
        && Objects.equals(id, order.id)
        && Objects.equals(timestamp, order.timestamp)
        && Objects.equals(serveTime, order.serveTime)
        && Objects.equals(user, order.user)
        && Objects.equals(restaurant, order.restaurant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timestamp, seats, tip, serveTime, user, restaurant);
  }

  //returns order's info
  @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", timestamp="
        + timestamp
        + ", seats="
        + seats
        + ", tip="
        + tip
        + ", serveTime="
        + serveTime
        + ", user="
        + user
        + ", restaurant="
        + restaurant
        + '}';
  }

  public boolean isProcessed() {
    return processed;
  }

  public void setProcessed(boolean processed) {
    this.processed = processed;
  }

  // controlling a addDishToCategory in an order

  public void addDish(Dish dish) {
    this.dishes.add(dish);
  }

  public void removeDish(Dish dish) {
    this.dishes.remove(dish);
  }

  public List<Dish> getDishes() {
    return dishes;
  }

  public void setDishes(List<Dish> dishes) {
    this.dishes = dishes;
  }

  public boolean isPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }

  public boolean isRejected() {
    return rejected;
  }

  public void setRejected(boolean rejected) {
    this.rejected = rejected;
  }
}
