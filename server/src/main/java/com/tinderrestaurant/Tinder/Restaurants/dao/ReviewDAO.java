package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.exception.NoPermissionForResourceException;
import com.tinderrestaurant.Tinder.Restaurants.exception.ResourceUnavailableException;
import com.tinderrestaurant.Tinder.Restaurants.model.Review;
import com.tinderrestaurant.Tinder.Restaurants.repository.ReviewRepository;
import com.tinderrestaurant.Tinder.Restaurants.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * retrieving/storing reviews from/to database
 */

@Service
public class ReviewDAO implements ReviewService {

    public final ReviewRepository reviewRepository;

    @Autowired
    public ReviewDAO(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * finds all reviews in the database
     *
     * @return  the list of reviews
     */

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    /**
     * finding the review using its id
     *
     * @param id    of review
     * @return      found review with the provided id
     * @throws      ResourceUnavailableException if the review is not found
     */

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceUnavailableException("Review", "id", id));

    }

    /**
     * saves the provided review, and returns it
     *
     * @param review    review to be saved
     * @return          saved review
     */

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    /**
     * delete the review from the database after finding it using its id,
     *
     * @param id    of review
     */

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);

    }

    /**
     * Checking if the review exists by its id, and then
     * update it by replacing the old value with the passed parameter
     *
     * @param review        new review
     * @param id            of existing review
     * @return              updated review
     */

    @Override
    public Review update(Review review, Long id) {
        Review existing = findById(id);
        if (reviewRepository.existsById(id)) {
            review.setId(existing.getId());
            review.setRestaurant(existing.getRestaurant());
            review.setUser(existing.getUser());
            review.setTimestamp(existing.getTimestamp());
        }
        return save(review);

    }
}
