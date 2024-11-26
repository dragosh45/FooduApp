package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.model.Order;
import com.tinderrestaurant.Tinder.Restaurants.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class OrderDAOTest {

  private OrderDAO orderDAO;
  private OrderRepository orderRepository;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    orderRepository = Mockito.mock(OrderRepository.class);
//    orderDAO = new OrderDAO(orderRepository);
  }

  @Test
  public void seeIfInjectedDAO() {
    assertThat(orderDAO).isNotNull();
  }

  @Test
  public void seeIfInjectedRepository() {
    assertThat(orderRepository).isNotNull();
  }

  @Test
  public void saveReturnsNotNull() {
    Mockito.when(orderRepository.save(any(Order.class))).thenReturn(new Order());
    Order order = new Order();
    assertThat(orderDAO.save(order)).isNotNull();
  }

  @Test
  public void saveOrderReturnsNewOrderWithId() {
    Mockito.when(orderRepository.save(any(Order.class)))
        .thenAnswer(
            (Answer<Order>)
                invocation -> {
                  Object[] arguments = invocation.getArguments();
                  if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                    Order order = (Order) arguments[0];
                    order.setId(1L);
                    return order;
                  }

                  return null;
                });

    Order order = new Order();
    assertThat(orderDAO.save(order)).isNotNull();
    assertThat(orderDAO.save(order).getId()).isEqualTo(1L);
  }

  @Test
  public void deleteOrderWorks() {
    orderDAO.delete(any(Long.class));
    Mockito.verify(orderRepository, Mockito.times(1)).deleteById(any(Long.class));
  }
}
