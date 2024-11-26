package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.model.Review;
import com.tinderrestaurant.Tinder.Restaurants.repository.OrderRepository;
import com.tinderrestaurant.Tinder.Restaurants.repository.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ReviewDAOTest {
    @Mock private ReviewDAO reviewDAO;
    @Mock private ReviewRepository reviewRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reviewRepository = Mockito.mock(ReviewRepository.class);
    }

    @Test
    public void newReviewTest() {
        Mockito.when(reviewDAO.save(any(Review.class))).thenReturn(new Review("good", 3));
        Review review = new Review("good", 3);
        assertThat(reviewDAO.save(review)).isNotNull();
    }

    @Test
    public void newReviewWithIdTest() {
        Mockito.when(reviewDAO.save(any(Review.class)))
                .then(
                        (Answer<Review>) invocation -> {
                            Object[] arguments = invocation.getArguments();
                            if (arguments != null && arguments.length > 0 && arguments[0] != null) {

                                Review review = (Review) arguments[0];
                                review.setId(new Long(1));
                                return review;
                            }
                            return null;
                        });
        Review review = new Review("good", 3);
        assertThat(reviewDAO.save(review)).isNotNull();
    }

    @Test
    public void deleteReviewTest() {
        reviewDAO.delete(new Long(1));
    }
}
