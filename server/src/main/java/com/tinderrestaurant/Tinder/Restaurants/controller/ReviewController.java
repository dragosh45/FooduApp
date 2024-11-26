package com.tinderrestaurant.Tinder.Restaurants.controller;

import com.tinderrestaurant.Tinder.Restaurants.model.Review;
import com.tinderrestaurant.Tinder.Restaurants.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * this class to be used in sending/receiving from/to the client.
 */

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Get all reviews
     *
     * @return - list of reviews available in the database
     */
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.findAll();
    }


    /**
     * Get review by id
     *
     * @param id - user_id
     * @return - review with ID if it exists in the db
     */
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable(value = "id") Long id) {
        return reviewService.findById(id);
    }

    /**
     * Create review and save it to database
     *
     * @param review  review object
     * @return            created review
     */
    @PostMapping
    public Review saveReview(@Valid @RequestBody Review review) {
        return reviewService.save(review);
    }

    /**
     * updating review's information
     *
     * @param id            of review
     * @param review        review to be updated
     * @return              updated review
     */
    @PutMapping("/{id}")
    public Review updateReview(
            @PathVariable(value = "id") Long id, @Valid @RequestBody Review review) {
        return reviewService.update(review, id);
    }

    /**
     * deleting a review using its id
     *
     * @param id    of review
     */
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable(value = "id") Long id) {
        reviewService.delete(id);
    }
}

