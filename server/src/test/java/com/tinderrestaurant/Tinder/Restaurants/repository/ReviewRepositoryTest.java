package com.tinderrestaurant.Tinder.Restaurants.repository;

import com.tinderrestaurant.Tinder.Restaurants.model.Login;
import com.tinderrestaurant.Tinder.Restaurants.model.Review;
import com.tinderrestaurant.Tinder.Restaurants.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ReviewRepositoryTest {
    @Autowired private ReviewRepository reviewRepository;

    private Review review;
    private User user;

    @Before
    public void setUp() {
        user =
                new User(
                        "Rahaf",
                        "Aln",
                        "Rahaf@mail.com",
                        new Login("pass123"),
                        "url://photo",
                        false,
                        false,
                        null);
        review = new Review("good", 3);
    }

    @Test
    public void saveReviewAndFindById() {
        review = reviewRepository.save(review);
        assertThat(reviewRepository.findById(review.getId())).hasSameHashCodeAs(review);
    }

    @Test
    public void deleteReview() {
        review = reviewRepository.save(review);
        long id = review.getId();
        reviewRepository.delete(review);
        assertThat(reviewRepository.findById(id)).isEmpty();
    }
}
